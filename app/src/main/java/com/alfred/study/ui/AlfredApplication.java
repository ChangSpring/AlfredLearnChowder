package com.alfred.study.ui;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by Alfred on 16/7/19.
 */
public class AlfredApplication extends TinkerApplication {

    public AlfredApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.alfred.study.ui.AlfredApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
