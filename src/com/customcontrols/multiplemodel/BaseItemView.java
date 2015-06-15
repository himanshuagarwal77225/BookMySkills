/*
 * Copyright 2015 chenupt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.customcontrols.multiplemodel;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by chenupt@gmail.com on 2015/2/13.
 * Description : Your item view could extend this for its itemEntity and position.
 */
public abstract class BaseItemView<T> extends FrameLayout implements IItemView<T>, IPosition{

    protected ItemEntity<T> model;
    protected int viewPosition;

    public BaseItemView(Context context) {
        super(context);
    }

    public BaseItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setModel(ItemEntity<T> entity){
        model = entity;
    }

    public abstract void bindView();

    @Override
    public void bindView(ItemEntity<T> model) {
        // Singleton depends on view's model saved last time.
        // If your item view does not extend from BaseItemView, you should check the cache timestamp if you need.
        if(!ItemEntityUtil.checkCache(this.model, model)){
            setModel(model);
            bindView();
        }
    }

    @Override
    public void bindViewPosition(int position) {
        viewPosition = position;
    }

}
