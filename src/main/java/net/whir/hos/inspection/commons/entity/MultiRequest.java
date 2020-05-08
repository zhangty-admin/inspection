package net.whir.hos.inspection.commons.entity;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: zty
 * @Date: 2020/5/6 2:34 下午
 */
@Slf4j
public class MultiRequest {


    /**
     * Get http 的方式
     *
     * @param paramMap
     * @param url
     * @return
     */
    public static String get(Map<String, String> paramMap, String url) {
        String result = "";
        HttpGet get = new HttpGet(url);
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            List<NameValuePair> params = setHttpParams(paramMap);
            String param = URLEncodedUtils.format(params, "UTF-8");
            get.setURI(URI.create(url + "?" + param));
            HttpResponse response = httpClient.execute(get);
            result = getHttpEntityContent(response);

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                result = "服务器异常";
            }
        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        } finally {
            get.abort();
        }
        return result;
    }

    public static List<NameValuePair> setHttpParams(Map<String, String> paramMap) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, String>> set = paramMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return params;
    }

    public static String getHttpEntityContent(HttpResponse response) throws UnsupportedOperationException, IOException {
        String result = "";
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream in = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                strber.append(line + '\n');
            }
            br.close();
            in.close();
            result = strber.toString();
        }

        return result;
    }

    /**
     * Post json http 的方式
     *
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    public static String doPostData(String url, String json) throws Exception {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        String result = "";
        HttpResponse res = null;
        try {
            StringEntity s = new StringEntity(json.toString(), "UTF-8");
            s.setContentType("application/json");
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json; charset=utf-8");
            post.setEntity(s);
            res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(res.getEntity());
                return HttpStatus.SC_OK + "";
            }
        } catch (Exception e) {
            if (res == null) {
                return "HttpResponse 为 null!";
            }
            throw new RuntimeException(e);
        }
        if (res == null || res.getStatusLine() == null) {
            return "无响应";
        }
        return res.getStatusLine().getStatusCode() + "";
    }

}
