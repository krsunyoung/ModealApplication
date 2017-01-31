package com.ff.modealapplication.app.core.service;

import com.ff.modealapplication.andorid.network.JSONResult;
import com.ff.modealapplication.app.core.vo.ItemVo;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.List;

/**
 * Created by BIT on 2017-01-24.
 */

public class SearchService {
    public List<ItemVo> searchList(){
        String url="192.168.1.15:8088/modeal/list/search";

        HttpRequest httpRequest = HttpRequest.get(url);

        httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON);
        httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON);
        httpRequest.connectTimeout(3000);
        httpRequest.readTimeout(3000);

        int responseCode=httpRequest.code();
        if(responseCode != HttpURLConnection.HTTP_OK){
            throw new RuntimeException("HTTP Response :" + responseCode);
        }

        JSONResultSearhList jsonResultSearchList = fromJSON(httpRequest, JSONResultSearhList.class);

        return jsonResultSearchList.getData();
    }

    public class JSONResultSearhList extends JSONResult<List<ItemVo>>{
    }

    protected <V> V fromJSON(HttpRequest request, Class<V> target){
        V v= null;
        try{
            Gson gson = new GsonBuilder().create();

            Reader reader = request.bufferedReader();
            v=gson.fromJson(reader, target);

            reader.close();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return v;
    }

}

