package com.ljk.controller.admin;

import com.aliyun.oss.OSS;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ljk.pojo.Blog;
import com.ljk.pojo.FileDTO;
import com.ljk.pojo.User;
import com.ljk.service.BlogService;
import com.ljk.service.TagService;
import com.ljk.service.TypeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: BlogController
 * author: JKL
 * date: 2021/2/21 21:45
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private OSS ossClient;

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endpoint;

    @Value("${spring.cloud.alicloud.access-key}")
    private String accessId;

    @Value("${spring.cloud.alicloud.bucketName}")
    private String bucketName;

    //获取博客列表
    @RequestMapping("/blogs")
    public String blogs(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {

        PageHelper.startPage(pageNum, 5);
        List<Blog> list = blogService.getAllBlog();
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(list);

        model.addAttribute("types", typeService.getAllType());
        model.addAttribute("pageInfo", pageInfo);
        return "admin/blogs";
    }

    public void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.getAllType());
        model.addAttribute("tags", tagService.getAllTag());
    }

    //跳转到博客新增页面
    @GetMapping("/blogs/input")
    public String input(Model model) {
        model.addAttribute("types", typeService.getAllType());
        //给前端返回一个blog对象
        model.addAttribute("blog", new Blog());
        setTypeAndTag(model);
        return "admin/blogs_input";
    }

    //博客新增编辑
    @PostMapping("/blogs")
    public String post(Blog blog, @RequestParam("file") MultipartFile pictureFile, HttpSession session, RedirectAttributes attributes, HttpServletRequest request) {

        //设置user属性
        blog.setUser((User) session.getAttribute("user"));
        blog.setUserId(blog.getUser().getId());
        blog.setTags(tagService.getTagByString(blog.getTagIds()));

        String file = null;
        try {
            file = upload(pictureFile, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        blog.setFirstPicture(file);

        int b;
        if (blog.getId() == null) {
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog);
        }
        if (b == 0) {
            attributes.addFlashAttribute("message", "操作失败！");
        } else {
            attributes.addFlashAttribute("message", "操作成功！");
        }

        return "redirect:/admin/blogs";
    }


    //  跳转到博客编辑页面
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        Blog blog = blogService.getBlogById(id);
        //将tags集合转换为tagIds字符串
//       blog.init();
//       //给前端object返回一个blog对象
        model.addAttribute("blog", blog);
        setTypeAndTag(model);
        return "admin/blogs_input";
    }

    //  删除博客
    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }

    //搜索博客
    @PostMapping("/blogs/search")
    public String searchBlogs(Blog blog, @RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
        //分页查询
        PageHelper.startPage(pageNum, 5);
        List<Blog> allBlog = blogService.searchAllBlog(blog);
        //分页结果对象
        PageInfo<Blog> pageInfo = new PageInfo<>(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("message", "查询成功");
        setTypeAndTag(model);
        return "admin/blogs";
    }

    //上传图片地址
    public String upload(MultipartFile pictureFile, HttpServletRequest request) throws IOException {

//      获取提交文件名称
        String filename = pictureFile.getOriginalFilename();
//      定个时间戳，避免名称重复
        String newName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + filename;
//      定义上传文件存放的路径
        String filePath = request.getScheme() + "://" + request.getServerName()
//            + ":"  + request.getServerPort() //端口
                + "/images/" + newName;
        //写入文件
        File saveFile = new File("/home/images/", newName);
        pictureFile.transferTo(saveFile);//文件保存

        return filePath;
    }

    //阿里云OSS上传文件
    @RequestMapping("/OssUpload")
    @ResponseBody
    public FileDTO OssUpload(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file) {
        FileDTO fileDTO = new FileDTO();
        try {
            String fileName = file.getOriginalFilename();
            ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(file.getBytes()));
            //上传到阿里云上的图片地址为：https://bucketname.endpoint/filename
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            fileDTO.setSuccess(1);
            fileDTO.setUrl(url);
            fileDTO.setMessage("上传图片成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭client
            ossClient.shutdown();
        }
        return fileDTO;
    }

}
