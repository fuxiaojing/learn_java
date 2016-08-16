/**
 * Copyright (c) 2015, TP-Link Co.,Ltd.
 * Author:  chenbiren <chenbiren@tp-link.net>
 * Created: 2015-11-12
 */
package cc.ty.play.common.zk;

import java.util.List;

public interface ChildListener {
    void childChanged(String path, List<String> children);
}
