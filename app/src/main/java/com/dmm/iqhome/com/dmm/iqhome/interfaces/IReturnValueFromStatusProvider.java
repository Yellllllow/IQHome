package com.dmm.iqhome.com.dmm.iqhome.interfaces;

import com.dmm.iqhome.Device;

import java.util.List;

/**
 * Created by waldekd on 2/10/16.
 */
public interface IReturnValueFromStatusProvider {
    void GetValueReturnedByStatusProvider(List<Device> devices);
}
