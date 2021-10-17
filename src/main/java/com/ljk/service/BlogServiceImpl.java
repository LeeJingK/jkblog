package com.ljk.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ljk.NotFoundException;
import com.ljk.dao.BlogDao;
import com.ljk.pojo.Blog;
import com.ljk.pojo.Tag;
import com.ljk.util.MarkdownUtils;
import com.ljk.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * description: BlogServiceImpl
 * author: JKL
 * date: 2021/2/23 22:20
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogDao blogDao;

//    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private RedisUtils redisUtils;

    public BlogServiceImpl(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Override
    public List<Blog> getAllBlog() {

        List<Blog> blogList = blogDao.getAllBlog();

        return blogList;
    }

    @Override
    public int saveBlog(Blog blog) {
        //删除所有缓存
        Set<String> keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        //保存博客
        blogDao.saveBlog(blog);
        //将对应的标签数据存到t_blog_tags表中
        List<Tag> tags = blog.getTags();
        for (Tag tag : tags) {
            Map<String, Long> map = new HashMap<>(16);
            //无法获取自增的id,在mybatis里设置userGenerateKeys
            map.put("tagsId", tag.getId());
            map.put("blogsId", blog.getId());
            blogDao.saveBlogAndTag(map);
        }
        return 1;
    }

    @Override
    public int updateBlog(Blog blog) {
        Set<String> keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
        blog.setUpdateTime(new Date());
//      将标签的数据存到t_blog_tags表中
        List<Tag> tags = blog.getTags();
        for (Tag tag : tags) {
            Map<String, Long> map = new HashMap<>(16);
            //无法获取自增的id,在mybatis里设置userGenerateKeys
            map.put("tagsId", tag.getId());
            map.put("blogsId", blog.getId());
            blogDao.saveBlogAndTag(map);
        }
        return blogDao.updateBlog(blog);
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogDao.getBlogById(id);
    }

    @Override
    public void deleteBlog(Long id) {
        Set<String> keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
        blogDao.deleteBlog(id);
    }

    @Override
    public List<Blog> searchAllBlog(Blog blog) {
        return blogDao.searchAllBlog(blog);
    }

    @Override
    public PageInfo<Blog> getIndexBlog(int pageNum, int pageSize) {
        String key = "blog";
        List<Blog> blogList = new ArrayList<>();

        Set<Object> obj = redisUtils.getPage(key, pageNum, pageSize);
        if (!obj.isEmpty()) {
            if (obj instanceof HashSet<?>) {
                for (Object aObj : obj) {
                    String blogId = (String) aObj;
                    Blog blog = (Blog) redisUtils.hmGet(key, blogId);
                    blogList.add(blog);

//                    logger.info("==================" + blog);
                }
            }
        } else {
            //没有缓存则查询数据库
            blogList = blogDao.getIndexBlog();
//          redisUtils.hmGet(key, blogList);
            for (Blog blog : blogList) {
                String hkey = blog.getId().toString();
                if (blog.isPublished()) {
                    blogList.add(blog);
                }
                redisUtils.setPage(key, hkey, Double.parseDouble(hkey), blog);
            }
        }

        Page<Blog> blogPage = new Page<>();
        blogPage.setPageNum(pageNum);
        blogPage.setPageSize(pageSize);
        blogPage.setTotal(redisUtils.getPageSize("blog"));

        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogPage);

        blogPageInfo.setList(blogList);
        return blogPageInfo;

//        String indexBlog = redisTemplate.opsForValue().get("keyIndexBlog");
//
//        logger.info("从缓存取" + indexBlog);
//        if (indexBlog == null) {
//            List<Blog> IndexBlogList = blogDao.getIndexBlog();
//            indexBlog = JSON.toJSONString(IndexBlogList);
//            logger.info("加入缓存：" + indexBlog);
//            redisTemplate.opsForValue().set("keyIndexBlog", indexBlog);
//        }
//        List<Blog> IndexBlogList = JSON.parseArray(indexBlog, Blog.class);
//
//        return IndexBlogList;
    }

    @Override
    public List<Blog> getAllRecommendBlog() {
        return blogDao.getAllRecommendBlog();
    }

    @Override
    public List<Blog> getSearchBlog(String query) {
        return blogDao.getSearchBlog(query);
    }

    @Override
    public Blog getDetailedBlog(Long id) {

        Blog blog = blogDao.getDetailedBlog(id);

//        将Markdown格式转换成html
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));  //将Markdown格式转换成html
        blogDao.getCommentCountById(id);
        return blog;
    }

    @Override
    public void saveViews(int views, Long id) {
        blogDao.saveViews(views, id);
    }

    @Override
    public List<Blog> getByTypeId(Long id) {
        return blogDao.getByTypeId(id);
    }

    @Override
    public List<Blog> getByTagId(Long id) {
        return blogDao.getByTagId(id);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        //set去重
        List<String> years = blogDao.findGroupYear();
        Set<String> set = new HashSet<>(years);
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : set) {
            map.put(year, blogDao.findByYear(year));
        }
        return map;
    }

    @Override
    public int countBlog() {
        return blogDao.getAllBlog().size();
    }


}
