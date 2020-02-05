package com.forward.logicalguess;

import android.app.Dialog;

/**
 * Created by ljh on 18-3-23.
 */

public interface OnConfirmListener {
    void cancel(Dialog dialog);

    void confirm(Dialog dialog);
}
