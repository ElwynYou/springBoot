/**
 * @Description: 全局js引入配置文件
 * @Author elwyn
 * @Date 2017/5/20 17:38
 * @Email elonyong@163.com
 */

window.jQuery || (function () {

    document.write([
        '<script src="js/jquery_3.2.1.js"></script>',
        '<script src="js/bootstrap_3.3.7.js"></script>',
        '<script src="plugins/layui/layui.js"></script>',
        /*css==============================================================================================*/
        '<link rel="stylesheet" href="css/global.css" media="all">',
        '<link rel="stylesheet" href="plugins/font-awesome/css/font-awesome.min.css">',
        '<link href="plugins/layui/css/layui.css" rel="stylesheet" media="all"/>',
        '<link href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" rel="stylesheet">',
    ].join(''));
})();