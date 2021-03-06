package com.ff.modealapplication.app.core.service;


import com.ff.modealapplication.andorid.network.JSONResult;
import com.ff.modealapplication.app.core.util.Base;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by BIT on 2017-01-19.
 */

public class MainService {
    private String url = Base.url + "modeal/mainapp/";

    // 앱 처음 켰을때는 모든 리스트를 최신순으로 띄움, GPS정보를 얻어서 쿠키에 저장
    // 그 후로 켰을때는 저장되어있는 GPS정보와 반경을 토대로 리스트 띄움
    public List<Map<String, Object>> MainItemList(String latitude, String longitude, int range){
        HttpRequest httpRequest = HttpRequest.post(url + "list");

        httpRequest.contentType(HttpRequest.CONTENT_TYPE_FORM);
        httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
        httpRequest.connectTimeout(5000);
        httpRequest.readTimeout(5000);

        httpRequest.send("latitude=" +
                latitude + "&longitude=" + longitude +"&range=" + range);

        int responseCode=httpRequest.code();
        if(responseCode != HttpURLConnection.HTTP_OK){
            throw new RuntimeException("HTTP RESPONSE :" + responseCode);
        }

        JSONResultMainList jsonResultMainList = fromJSON(httpRequest, JSONResultMainList.class);

        return jsonResultMainList.getData();
    }

    private class JSONResultMainList extends JSONResult<List<Map<String, Object>>>{
    }

    protected <V> V fromJSON(HttpRequest request, Class<V> target){
        V v = null;
        try{
            Gson gson = new GsonBuilder().create();

            Reader reader = request.bufferedReader();
            v = gson.fromJson(reader, target);

            reader.close();
        }catch (IOException ex){
            throw new RuntimeException(ex);
        }
        return v;
    }
}
