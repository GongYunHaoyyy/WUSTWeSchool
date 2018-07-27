package com.gongyunhaoyyy.wustweschool.util;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lqy on 2016/7/15.
 * 此类通过android提供的Ksoap2包调用webservice接口
 * 其中接口不可泄露！！！
 */
public class Ksoap2 {

    private static final String key="webservice_whkdapp";
    // 命名空间
    private static final String nameSpace = "http://webservices.qzdatasoft.com";
    // EndPoint
    private static final String endPoint = "http://jwxt.wust.edu.cn/whkjdx/services/whkdapp";

    private static final String baseSoapAction = "http://webservices.qzdatasoft.com/";

    public static String getScoreInfo(String xh) throws IOException, XmlPullParserException {
        // 调用的方法名称
        String methodName = "getxscj";

        SoapObject localSoapObject = new SoapObject(nameSpace, methodName);
        localSoapObject.addProperty("in0", xh);
        return getResult(methodName, localSoapObject, 1);
    }

    public static String getCourseInfo(String xh, String xq) throws IOException, XmlPullParserException {
        // 调用的方法名称
        String methodName = "getyxkclb";
        SoapObject localSoapObject =new  SoapObject(nameSpace, methodName);
        localSoapObject.addProperty("in0", xh);
        localSoapObject.addProperty("in1", xq);
        return getResult(methodName, localSoapObject, 2);
    }

    public static String getLoginInfo(String xh, String pwd) throws IOException, XmlPullParserException {
        String methodName = "newlogin";

        SoapObject localSoapObject = new SoapObject(nameSpace, methodName);
        localSoapObject.addProperty("in0", xh);
        localSoapObject.addProperty("in1", pwd);
        return getResult(methodName, localSoapObject, 2);
    }
    //选课阶段
    public static String getXkjd(String paramString) throws IOException, XmlPullParserException {

        String methodName = "getxkjdlb";
        SoapObject localSoapObject = new SoapObject(nameSpace, methodName);
        localSoapObject.addProperty("in0", paramString);
        return getResult(methodName, localSoapObject, 1);
    }
    /**
     * 获取可选课程
     * @param paramString1 学生学号
     * @param paramString2 选课控制子表ID
     * @param paramString3 学年学期ID
    Json字符串
     * @return
     */
    public static String getKxkc(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
            throws IOException, XmlPullParserException {
        String methodName = "getkykc";
        SoapObject localSoapObject = new SoapObject(nameSpace, methodName);
        localSoapObject.addProperty("in0", paramString1);
        localSoapObject.addProperty("in1", paramString2);
        localSoapObject.addProperty("in2", paramString3);
        localSoapObject.addProperty("in3", paramString4);
        localSoapObject.addProperty("in4", paramString5);
        localSoapObject.addProperty("in5", paramString6);
        return getResult(methodName, localSoapObject, 6);
    }

    public static String getTerm() throws IOException, XmlPullParserException {
        String methodName = "getpjxnxq";
        return getResult(methodName, new SoapObject(nameSpace, methodName), 0);
    }
    /**
     * 获取评价批次名称
     * @return
    Pj05id 评价批次ID
    Pjpcmc 评价批次名称
    Json字符串
     * @param xnxq01id
     */
    public static String getPjpcmc(String xnxq01id) throws IOException, XmlPullParserException {
        String methodName = "getpjpcmc";

        SoapObject localSoapObject = new SoapObject(nameSpace, methodName);
        localSoapObject.addProperty("in0", xnxq01id);
        return getResult(methodName, localSoapObject, 1);
    }

    /**
     *
     * @param paramString
     * @param paramSoapObject
     * @param paramInt
     * @return
     */
    static String getResult(String paramString, SoapObject paramSoapObject, int paramInt) throws IOException, XmlPullParserException {
        Date localDate = new Date();
        String str1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(localDate);
        String str2 = Md5Util.MD5(key + str1).substring(2).toLowerCase();
        int i = paramInt + 1;
        paramSoapObject.addProperty("in"+paramInt, str1);
        paramSoapObject.addProperty("in"+i, str2);
        SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(110);
        localSoapSerializationEnvelope.bodyOut = paramSoapObject;
        localSoapSerializationEnvelope.dotNet = true;
        localSoapSerializationEnvelope.setOutputSoapObject(paramSoapObject);
        new HttpTransportSE(endPoint).call(baseSoapAction + paramString, localSoapSerializationEnvelope);
        return ((SoapObject)localSoapSerializationEnvelope.bodyIn).getProperty("out").toString();
    }
}
