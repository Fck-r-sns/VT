package com.vt.timedriven;

import com.vt.serialization.ValuesChangeHistory;

/**
 * Created by Fck.r.sns on 08.08.2015.
 */

public interface TimeDrivenExecutable {
    public boolean execute(); // checks Environment.gameTime
    public void setValuesHistory(ValuesChangeHistory history); // for rewind
}
