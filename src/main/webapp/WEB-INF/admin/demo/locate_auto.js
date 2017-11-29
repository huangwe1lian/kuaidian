;(function(){
    var LocateToyota = {
        running:false,// false || true
        cookieName:'fawtoyotaLocate',
        callbackQueue:[],// 定位回调函数队列
        locateDate: {},//暂时记录locate返回的数据
        defaultData:{proId:5, cityId:1, pcProId:5, pcCityId:1, whoisCode:440100},
        dataTypeAuto:'', //数据来源： 'region_ipArea' || 'default' || '' || 'user'
        /*
        Locate.err:[0], //记录错误数量，统一在Locate.err里记录
            [0]:'', //记录是否有错误： 0 || 1 || 2 || 3 ...
            ['h5']:'', //记录h5定位错误： 'NotHtml5' || 'RejectShare' || 'GetError' || 'Timeout'
            ['ipAreaCoordJson']:'',//记录 ipAreaCoordJson 定位接口返回错误信息
            ['ipJson']:'', //记录 ipJson 定位接口返回错误信息
            ['region_ipArea']:'', //记录 region_ipArea 接口错误信息
            ['interface']:'' //记录接口请求异常，网络或者接口异常： 'ipJson' || 'ipAreaCoordJson'
            ['other']:'' //记录其它异常，如用户设置地区时异常
        */
        getLocateCookie: function(){
            return Locate.parseResult(Locate.getCookie(LocateToyota.cookieName));
        },
        // 通过定位城市code:cityCode
        setByCityCode: function(cityCode, callbackSetSucc, callbackSetErr) {
            if(LocateToyota.running) {
                var err = [1];
                err['other'] = 'Lacate is running!';
                callbackSetErr({err:err});
            }else{
                LocateToyota.running = true;
                callbackSetSucc && (LocateToyota.callbackQueue.push(callbackSetSucc));
                callbackSetErr && (Locate.callbackSetErr = callbackSetErr);
                var url = "http://lrc.pcauto.com.cn:8080/api/iparea?cityCode=" + cityCode;
                Locate.getScript({
                    url: url,
                    interface: 'region_ipArea',
                    callbackSucc: LocateToyota.setByCityCodeSucc,
                    callbackSuccName: 'setByCityCodeSucc_auto',
                    callbackErr: LocateToyota.setByCityCodeErr
                });
            }
        },
        // 通过定位城市ID:cityId
        setByCityId: function(cityId, callbackSetSucc, callbackSetErr) {
            if(LocateToyota.running) {
                var err = [1];
                err['other'] = 'Lacate is running!';
                callbackSetErr({err:err});
            }else{
                LocateToyota.running = true;
                callbackSetSucc && (LocateToyota.callbackQueue.push(callbackSetSucc));
                callbackSetErr && (Locate.callbackSetErr = callbackSetErr);
                var url = "http://lrc.pcauto.com.cn:8080/api/iparea?cityId=" + cityId;
                Locate.getScript({
                    url: url,
                    interface: 'region_ipArea',
                    callbackSucc: LocateToyota.setByCityCodeSucc,
                    callbackSuccName: 'setByCityIdSucc_auto',
                    callbackErr: LocateToyota.setByCityCodeErr
                });
            }
        },
        setByCityCodeSucc: function(data) {
            if(!data.result) {
                if(Locate.callbackSetErr) {
                    var dataReturn = {err: data.msg};
                    Locate.callbackSetErr(dataReturn);
                    delete Locate.callbackSetErr;
                }
                LocateToyota.running = false;
            }else{
                var dataLocate = {
                    //proCode: data.cityData.proCode,
                    pro: data.cityData.pro + '省',
                    cityCode: data.cityData.cityCode,
                    city: data.cityData.name + '市',
                    dataType: 'user',
                    expires: +new Date + 1296 * 1e6 //过期时间 15天
                }
                var dataLocateToyota = {
                    proId:data.cityData.proId,
					pcProId: data.cityData.pcProId,
                    cityId:data.cityData.cityId,
					pcCityId: data.cityData.pcCityId,
                    dataTypeAuto: 'user'
                }
                if(LocateToyota.device == 'wap') {
                    dataLocateToyota.url = dataLocateToyota.url.replace(/www\.pcauto\.com\.cn/,"m\.pcauto\.com\.cn/x");
                }
                Locate.setCookie(Locate.cookieName, Locate.stringifyResult(dataLocate), 1024*24*3600*1000);
                Locate.setCookie(LocateToyota.cookieName, Locate.stringifyResult(dataLocateToyota), 1024*24*3600*1000);
                Locate.extend(dataLocate,dataLocateToyota);
                LocateToyota.doCallback(dataLocate);
            }
        },
        setByCityCodeErr: function(interface) {
            if(Locate.callbackSetErr) {
                var err = [1];
                err['other'] = interface + '接口请求失败';
                Locate.callbackSetErr({err:err});
                delete Locate.callbackSetErr;
                Locate.running = false;
            }
        },
        doCallbackSucc: function(data) {
        	alert(data.cityData.proId);
            var dataReturn = {
                proId: data.cityData.proId,
                cityId: data.cityData.cityId,
                pcProId: data.cityData.pcProId,
                pcCityId: data.cityData.pcCityId,
                dataTypeAuto: LocateToyota.dataTypeAuto
            }
            if(LocateToyota.device == 'wap') {
                dataReturn.url = dataReturn.url.replace(/www\.pcauto\.com\.cn/,"m\.pcauto\.com\.cn/x");
            }
            Locate.setCookie(LocateToyota.cookieName, Locate.stringifyResult(dataReturn), 1024*24*3600*1000);
            Locate.extend(dataReturn, LocateToyota.locateDate);
            LocateToyota.doCallback(dataReturn);
        },
        doCallback: function(data) {
            var queue = LocateToyota.callbackQueue, i = 0, length = queue.length;
            for(; i<length; i++) {queue[i].call(LocateToyota, data);}
            LocateToyota.callbackQueue = [];
            LocateToyota.dataTypeAuto = '';
            LocateToyota.running = false;
        },
        getByCityCodeSucc: function(data) {
            if(data.result) {
                LocateToyota.dataTypeAuto = 'region_ipArea';
                LocateToyota.doCallbackSucc(data);
            }else{
                LocateToyota.locateDate.err[0] += 1;
                LocateToyota.locateDate.err['region_ipArea'] = data.msg;
                doCallback(LocateToyota.locateDate);
            }
        },
        getByCityCodeErr: function(data) {
            LocateToyota.locateDate.err[0] += 1;
            LocateToyota.locateDate.err['other'] = 'region_ipArea接口请求失败';
            LocateToyota.doCallback(LocateToyota.locateDate);
        },
        getLocate: function(data) {
            if(data.dataType == "default") {
                Locate.extend(data, LocateToyota.defaultData);
                data.dataTypeAuto = "default";
                LocateToyota.doCallback(data);
            }else{
                LocateToyota.locateDate = data;
                var url = "http://lrc.pcauto.com.cn:8080/api/iparea?cityCode=" + data.cityCode;
                Locate.getScript({
                    url: url,
                    interface: 'region_ipArea',
                    callbackSucc: LocateToyota.getByCityCodeSucc,
                    callbackSuccName: 'getByCityCodeSucc_fawtoyota',
                    callbackErr: LocateToyota.getByCityCodeErr
                });
            }
        },
        init: function(obj) { //callback, device
            this.callbackQueue.push(obj.callback);
            this.device = obj.device || 'wap';

            if(!this.running) {
                this.running = true;
                var locationCookie = Locate.getLocateCookie(),
                    locationAutoCookie = this.getLocateCookie(),
                    now = +new Date;
                if (locationCookie && locationCookie.expires > now && locationAutoCookie) {
                    locationCookie.dataType = locationAutoCookie.dataTypeAuto = 'cookie';
                    Locate.extend(locationCookie,locationAutoCookie);
                    this.doCallback(locationCookie);
                } else {
                    Locate.init({
                        device: LocateToyota.device,
                        callback: LocateToyota.getLocate
                    });
                }
            }
        }
    };
    window.LocateToyota = LocateToyota;
})();