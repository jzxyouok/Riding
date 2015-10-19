/*
 Copyright (c) 2013 Roman Truba

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial
 portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ru.truba.touchgallery.GalleryWidget;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import ru.truba.touchgallery.TouchView.FileTouchImageView;

/**
 Class wraps file paths to adapter, then it instantiates {@link ru.truba.touchgallery.TouchView.FileTouchImageView} objects to paging up through them.
 */
public class FilePagerAdapter extends BasePagerAdapter {


    public FilePagerAdapter(Context context, List<String> resources)
	{
		super(context, resources);
	}

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        ((GalleryViewPager)container).mCurrentView = ((FileTouchImageView)object).getImageView();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position){
        final FileTouchImageView iv = new FileTouchImageView(mContext);
        iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        iv.setUrl(mResources.get(position));

        collection.addView(iv, 0);
        return iv;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
