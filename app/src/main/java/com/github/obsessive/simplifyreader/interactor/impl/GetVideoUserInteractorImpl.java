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
import com.github.obsessive.simplifyreader.bean.VideosListUserEntity;
import com.github.obsessive.simplifyreader.interactor.GetVideoUserInteractor;
import com.github.obsessive.simplifyreader.listeners.BaseSingleLoadedListener;
import com.github.obsessive.simplifyreader.utils.UriHelper;
import com.github.obsessive.simplifyreader.utils.VolleyHelper;
import com.google.gson.reflect.TypeToken;


public class GetVideoUserInteractorImpl implements GetVideoUserInteractor {

    private BaseSingleLoadedListener<VideosListUserEntity> loadedListener = null;

    public GetVideoUserInteractorImpl(BaseSingleLoadedListener<VideosListUserEntity> loadedListener) {
        this.loadedListener = loadedListener;
    }

    @Override
    public void getVideoUser(String requestTag, int user_id) {
        GsonRequest<VideosListUserEntity> gsonRequest = new GsonRequest<VideosListUserEntity>(
                UriHelper.getInstance().getVideoUserUrl(user_id),
                null,
                new TypeToken<VideosListUserEntity>() {
                }.getType(),
                new Response.Listener<VideosListUserEntity>() {
                    @Override
                    public void onResponse(VideosListUserEntity response) {
                        loadedListener.onSuccess(response);
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
