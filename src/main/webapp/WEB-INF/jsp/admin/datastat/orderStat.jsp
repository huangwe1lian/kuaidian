<%@page contentType="text/html; charset=UTF-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<%-- 线索量统计 --%>
<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" id="searchForm" action="" method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        选择时间范围：
                        <c:if test="${statType == 'day' || statType == 'week'}">
                            <input type="text" id="beginTime" name="beginTime" value="${beginTime}" readonly="true" /> ~ <input type="text" id="endTime" name="endTime" value="${endTime}" readonly="true" />
                        </c:if>
                        <c:if test="${statType == 'month'}">
                            <input id="beginTime" class="JQ_yearAndMonth required textInput readonly" type="text" name="beginTime" value="${beginTime}" size="10"/> ~ <input id="endTime" class="JQ_yearAndMonth required textInput readonly" type="text" name="endTime" value="${endTime}" size="10"/>
                        </c:if>
                    </td>
                    <td>
                        单位：
                        <input type="radio" name="statType" ${statType == 'day' ? 'checked' : ''} value="day"/>日
                        <input type="radio" name="statType" ${statType == 'week' ? 'checked' : ''} value="week"/>周
                        <input type="radio" name="statType" ${statType == 'month' ? 'checked' : ''} value="month"/>月
                    </td>
                </tr>
            </table>
            <div class="subBar">
                <ul>
                    <li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
                </ul>
            </div>
        </div>
    </form>
</div>
<div class="pageContent">
    <div layoutH="61">
        <div id="orderChart" style="width:100%;height:400px;"></div>
        <div id="sgOrderChart" style="width:100%;height:500px;"></div>
        <div>
            <b>地区：</b>
            <select name="provinceId" id="provinceId" onchange="getCity()">
                <option value="" >请选择省份</option>
            </select>
            <select name="cityId" id="cityId">
                <option value="">请选择城市</option>
            </select>
            <div id="dealerOrderChart" style="width:100%;height:400px;"></div>
        </div>
    </div>
</div>
<script src="/admin/js/echarts.common.min.js"></script>
<script type="text/javascript">
    var dealerOrderChart = echarts.init(document.getElementById('dealerOrderChart'));

    function setOption4DealerOrder(dealerOrderxAxis, dealerOrderSeries) {
        var dealerOption = {
            title: {
                text: '线索前十经销商'
            },
            tooltip: {},
            legend: {
                data:['经销商']
            },
            xAxis: {
                data: dealerOrderxAxis
            },
            yAxis: {},
            series: [{
                name: '经销商',
                type: 'bar',
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                },
                data: dealerOrderSeries
            }]
        };
        dealerOrderChart.setOption(dealerOption);
    }

    //生成区域经销商报表
    function areaDealerOrderChart() {
        var beginTime = $("#beginTime").val();
        var endTime = $("#endTime").val();
        var provinceId = $("#provinceId").val();
        var cityId = $("#cityId").val();
        if(${statType == 'month'}){
            beginTime += '-01';
            var dateArr = endTime.split("-");
            var endDate = new Date(dateArr[0], dateArr[1], 0);
            endTime += '-' + endDate.getDate();

        }

        $.getJSON('/admin/datastat/dealerOrderStat',
                {
                    "beginTime":beginTime,
                    "endTime":endTime,
                    "provinceId":provinceId,
                    "cityId":cityId
                },
                function (data) {
                    setOption4DealerOrder(data["dealerOrderxAxis"], data["dealerOrderSeries"])
                });
    }
    $("#provinceId,#cityId").change(function () {
        areaDealerOrderChart();
    });

    //地区选择
    function getProvince(){
        $.getScript("/api/province/list?callback=setProvince");
    }
    function setProvince(data){
        var options = '<option value="0">请选择省份</option>';
        for(var i=0;i < data.length;i++){
            options += '<option value="' + data[i].pId + '" >' + data[i].name + '</option>'
        }
        $("#provinceId").html(options);
    }

    function getCity(){
        var pid = $("#provinceId").val();
        $.getScript("/api/city/list?callback=setCity&pid=" +pid);
    }
    function setCity(data){
        var pid = $("#provinceId").val();
        var options = '<option value="0">请选择城市</option>';
        for(var i=0;i < data.length;i++){
            options += '<option value="' + data[i].cId + '" >' + data[i].name + '</option>'
        }
        $("#cityId").html(options);
    }

    $(document).ready(function(){
        //获取省份数据
        getProvince();

        //初始化时间控件
        if(${statType == 'month'}){
            setTimeout(function() { $(".JQ_yearAndMonth", navTab.getCurrentPanel()).simpleCanleder(); },200);
        } else {
            $("#beginTime").datepicker({
                minDate:"{%y-10}-%M-%d",
                maxDate:"{%y}-%M-{%d}",
                showDay:${statType == 'week' ? 1 : -1}
            });

            $("#endTime").datepicker({
                minDate:"{%y-10}-%M-%d",
                maxDate:"{%y}-%M-{%d}",
                showDay:${statType == 'week' ? 0 : -1}
            });
        }

        //初始化表单Action
        if(${statType == 'day'}){
            $("#searchForm").attr("action", "/admin/datastat/dayOrderStat");
        } else if(${statType == 'week'}){
            $("#searchForm").attr("action", "/admin/datastat/weekOrderStat");
        } else if(${statType == 'month'}){
            $("#searchForm").attr("action", "/admin/datastat/monthOrderStat");
        }

        //统计单位切换
        $("input[name='statType']").change(function () {
            var statType = $(this).val();
            var orderStatUrl;
            if(statType == 'day'){
                orderStatUrl = '/admin/datastat/dayOrderStat';
            } else if(statType == 'week'){
                orderStatUrl = '/admin/datastat/weekOrderStat';
            } else if(statType == 'month'){
                orderStatUrl = '/admin/datastat/monthOrderStat';
            }

            navTab.openTab("stat-order", orderStatUrl, {title:"线索量统计"})
        });

        //生成线索量走势报表
        var orderChart = echarts.init(document.getElementById('orderChart'));
        var orderOption = {
            title: {
                text: '线索量走势'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:['线索量']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type : 'category',
                boundaryGap : false,
                data: ${orderxAxis}
            },
            yAxis: {},
            series: [{
                name: '线索量',
                type: 'line',
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                },
                areaStyle: {normal: {}},
                data: ${orderSeries}
            }]
        };
        orderChart.setOption(orderOption);

        //生成各车系线索占比报表
        var sgOrderChart = echarts.init(document.getElementById('sgOrderChart'));
        var sgOrderOption = {
            title : {
                text: '各车系线索占比',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ${sgOrderLegend}
            },
            series : [
                {
                    name: '访问来源',
                    type: 'pie',
                    radius : '50%',
                    center: ['50%', '60%'],
                    data: ${sgOrderSeries},
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        sgOrderChart.setOption(sgOrderOption);

        //生成线索前十经销商报表
        setOption4DealerOrder(${dealerOrderxAxis}, ${dealerOrderSeries});
    });
</script>