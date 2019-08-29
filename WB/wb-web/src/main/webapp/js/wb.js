/**
 * Created by Administrator on 2016-10-19.
 */

;(function (win) {

    'use strict';

    var wb = typeof win.wb === 'object' ? win.wb : win.wb = {};

    wb.get = function (id) {
        return document.getElementById(id);
    };

}(window || {}));
