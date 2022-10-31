import com.liangguo.translator.app.translate.model.TransEngine
import com.liangguo.translator.config.LocalPref
import org.junit.Test
/**
 * @author ldh
 * 时间: 2022/10/27 22:35
 * 邮箱: 2637614077@qq.com
 */
class Test {

    @Test
    fun classNameTest() {
        println(TransEngine.javaClass.name.substringAfterLast("."))
        println(TransEngine.javaClass.simpleName)
        println(TransEngine.javaClass.canonicalName)
        println(TransEngine.javaClass.packageName)
        println(TransEngine.javaClass.typeName)
    }

    @Test
    fun apiConfigTest() {
        LocalPref.youDaoAppKey = "测试1"
        LocalPref.youDaoAppSecret = "测试2"
    }

}