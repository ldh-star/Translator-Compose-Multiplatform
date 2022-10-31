package com.liangguo.translator.logic.network.baidu


/**
 * @author ldh
 * 时间: 2022/10/24 16:09
 * 邮箱: 2637614077@qq.com
 */
object BaiduErrorCode {
    var BaiduTransApiErrorCode = arrayOf(
        "52000",
        "52001",
        "52002",
        "52003",
        "54000",
        "54001",
        "54003",
        "54004",
        "54005",
        "58000",
        "58001",
        "58002",
        "90107"
    )
    var BaiduTransApiErrorCodeMeaning = arrayOf(
        "成功",
        "请求超时",
        "系统错误",
        "未授权用户",
        "必填参数为空",
        "签名错误",
        "访问频率受限",
        "账户余额不足",
        "长query请求频繁",
        "客户端IP非法",
        "译文语言方向不支持",
        "服务当前已关闭",
        "认证未通过或已失效"
    )
    var BaiduTransApiErrorCodeSolutionDirection = arrayOf(
        "",
        "重试",
        "重试",
        "检查您的 appid 是否正确，或者服务是否开通",
        "检查是否少传参数",
        "请检查您的签名生成方法",
        "请降低您的调用频率",
        "请前往管理控制台为账户充值",
        "请降低长query的发送频率，3s后再试",
        "检查个人资料里填写的 IP地址 是否正确\n可前往管理控制平台修改\nIP限制，IP可留空",
        "检查译文语言是否在语言列表里",
        "请前往管理控制台开启服务",
        "请前往我的认证查看认证进度"
    )

    fun getMeaning(errorCode: String) = BaiduTransApiErrorCodeMeaning[BaiduTransApiErrorCode.indexOf(errorCode)]


    fun getSolution(errorCode: String) = BaiduTransApiErrorCodeSolutionDirection[BaiduTransApiErrorCode.indexOf(errorCode)]


}