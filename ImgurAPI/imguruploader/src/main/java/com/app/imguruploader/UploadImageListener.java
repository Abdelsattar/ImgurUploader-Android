package com.app.imguruploader;

import com.app.imguruploader.Response.Image;

/**
 * Created by Abd El-Sattar on 4/19/2016.
 */

public interface UploadImageListener {
    public void ImgurResponse(boolean status,Image result);
}
