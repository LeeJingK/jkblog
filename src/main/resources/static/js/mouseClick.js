/* 鼠标特效 */
var a_idx = 0;
var b_idx = 0;
/*   文字和颜色数组 */
var a = new Array("♥富强♥", "♥民主♥", "♥文明♥", "♥和谐♥", "♥自由♥", "♥平等♥", "♥公正♥", "♥法治♥", "♥爱国♥", "♥敬业♥", "♥诚信♥", "♥友善♥");
var b = new Array("#FF0000", "#FF7F00", "#ffb967", "#b6e3ff", "#ffbbed", "#89A3FF", "#8B00FF", "#FF0000", "#FF7F00", "#c4e1ff", "#ffaaff", "#aaffd4", "#8B00FF");
jQuery(document).ready(function ($) {
    $("body").click(function (e) {

        var $i = $("<span/>").text(a[a_idx]);
        a_idx = parseInt(12 * Math.random()); //文字随机数
        b_idx = parseInt(14 * Math.random()); //颜色随机数
        var x = e.pageX,
            y = e.pageY;
        $i.css({
            "z-index": 999,
            "font-size": "1.3em",            //字体大小
            "top": y - 20,
            "left": x,
            "position": "absolute",
            "font-weight": "bold",
            "color": b[b_idx]
        });
        $("body").append($i);
        $i.animate({
                "top": y - 180,
                "opacity": 0
            },
            1500,
            function () {
                $i.remove();
            });
    });
});