package com.zz.util.shengyuan;

import java.util.HashMap;
import java.util.Map;

public class WebToolKit {
    private static WebToolKit instance = null;
    private Map<Integer, String> httpCodeMap;

    private WebToolKit() {
    }

    public static WebToolKit getInstance() {
        if(instance == null) {
            instance = new WebToolKit();
            instance.setHttpCodeMap(instance.getErrorCode());
        }

        return instance;
    }

    public Map<Integer, String> getHttpCodeMap() {
        return this.httpCodeMap;
    }

    private void setHttpCodeMap(Map<Integer, String> httpCodeMap) {
        this.httpCodeMap = httpCodeMap;
    }

    private Map<Integer, String> getErrorCode() {
        Map<Integer, String> map = new HashMap();
        map.put(Integer.valueOf(100), "服务器返回此代码表示已收到请求的第一部分，正在等待其余部分。");
        map.put(Integer.valueOf(101), "请求者已要求服务器切换协议，服务器已确认并准备切换。 ");
        map.put(Integer.valueOf(200), "服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。 ");
        map.put(Integer.valueOf(201), "请求成功并且服务器创建了新的资源。 ");
        map.put(Integer.valueOf(202), "服务器已接受请求，但尚未处理。 ");
        map.put(Integer.valueOf(203), "服务器已成功处理了请求，但返回的信息可能来自另一来源。 ");
        map.put(Integer.valueOf(204), "服务器成功处理了请求，但没有返回任何内容。 ");
        map.put(Integer.valueOf(205), "重置内容,服务器成功处理了请求，但没有返回任何内容。");
        map.put(Integer.valueOf(206), "服务器成功处理了部分 GET 请求。 ");
        map.put(Integer.valueOf(300), "针对请求，服务器可执行多种操作。 服务器可根据请求者 (user agent) 选择一项操作，或提供操作列表供请求者选择。 ");
        map.put(Integer.valueOf(301), "请求的网页已永久移动到新位置。 服务器返回此响应（对 GET 或 HEAD 请求的响应）时，会自动将请求者转到新位置。");
        map.put(Integer.valueOf(302), "服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。");
        map.put(Integer.valueOf(303), "请求者应当对不同的位置使用单独的 GET 请求来检索响应时，服务器返回此代码。");
        map.put(Integer.valueOf(304), "自从上次请求后，请求的网页未修改过。 服务器返回此响应时，不会返回网页内容。 ");
        map.put(Integer.valueOf(305), "请求者只能使用代理访问请求的网页。 如果服务器返回此响应，还表示请求者应使用代理。 ");
        map.put(Integer.valueOf(307), "服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。 ");
        map.put(Integer.valueOf(400), "服务器不理解请求的语法。 ");
        map.put(Integer.valueOf(401), "请求要求身份验证。 ");
        map.put(Integer.valueOf(403), "服务器拒绝请求。");
        map.put(Integer.valueOf(404), "服务器找不到请求的网页。");
        map.put(Integer.valueOf(405), "禁用请求中指定的方法，通常是get或者post请求不匹配。 ");
        map.put(Integer.valueOf(406), "无法使用请求的内容特性响应请求的网页。 ");
        map.put(Integer.valueOf(407), "需要代理授权");
        map.put(Integer.valueOf(408), "服务器等候请求时发生超时。 ");
        map.put(Integer.valueOf(409), "服务器在完成请求时发生冲突。 ");
        map.put(Integer.valueOf(410), "如果请求的资源已永久删除，服务器就会返回此响应。 ");
        map.put(Integer.valueOf(411), "服务器不接受不含有效内容长度标头字段的请求。 ");
        map.put(Integer.valueOf(412), "服务器未满足请求者在请求中设置的其中一个前提条件。 ");
        map.put(Integer.valueOf(413), "服务器无法处理请求，因为请求实体过大，超出服务器的处理能力。 ");
        map.put(Integer.valueOf(414), "请求的 URI（通常为网址）过长，服务器无法处理。 ");
        map.put(Integer.valueOf(415), "请求的格式不受请求页面的支持。 ");
        map.put(Integer.valueOf(416), "如果页面无法提供请求的范围，则服务器会返回此状态代码。 ");
        map.put(Integer.valueOf(417), "服务器未满足期望请求标头字段的要求。 ");
        map.put(Integer.valueOf(500), "服务器遇到错误，无法完成请求。 ");
        map.put(Integer.valueOf(501), "服务器不具备完成请求的功能。 例如，服务器无法识别请求方法时可能会返回此代码。 ");
        map.put(Integer.valueOf(502), "服务器作为网关或代理，从上游服务器收到无效响应。 ");
        map.put(Integer.valueOf(503), "服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态。 ");
        map.put(Integer.valueOf(504), "服务器作为网关或代理，但是没有及时从上游服务器收到请求。 ");
        map.put(Integer.valueOf(505), "服务器不支持请求中所用的 HTTP 协议版本。 ");
        return map;
    }
}