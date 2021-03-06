/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.obsessive.simplifyreader.interactor.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.github.obsessive.library.utils.TLog;
import com.github.obsessive.simplifyreader.bean.ResponseMusicsListentity;
import com.github.obsessive.simplifyreader.interactor.MusicsInteractor;
import com.github.obsessive.simplifyreader.listeners.BaseMultiLoadedListener;
import com.github.obsessive.simplifyreader.utils.UriHelper;
import com.github.obsessive.simplifyreader.utils.VolleyHelper;
import com.google.gson.reflect.TypeToken;


public class MusicsInteracotrImpl implements MusicsInteractor {

    private BaseMultiLoadedListener<ResponseMusicsListentity> loadedListener = null;

    public MusicsInteracotrImpl(BaseMultiLoadedListener<ResponseMusicsListentity> loadedListener) {
        this.loadedListener = loadedListener;
    }

    @Override
    public void getMusicListData(String requestTag, String keywords, final int event_tag) {
        TLog.d(requestTag, UriHelper.getInstance().getDoubanPlayListUrl(keywords));
        GsonRequest<ResponseMusicsListentity> gsonRequest = new GsonRequest<ResponseMusicsListentity>(
                UriHelper.getInstance().getDoubanPlayListUrl(keywords),
                null,
                new TypeToken<ResponseMusicsListentity>() {
                }.getType(),
                new Response.Listener<ResponseMusicsListentity>() {
                    @Override
                    public void onResponse(ResponseMusicsListentity response) {
                        loadedListener.onSuccess(event_tag, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadedListener.onException(error.getMessage());
                    }
                }
        );

        gsonRequest.setShouldCache(true);
        gsonRequest.setTag(requestTag);

        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }
}
