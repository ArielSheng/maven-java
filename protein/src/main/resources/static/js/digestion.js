// 使用fetch API异步加载JSON文件
fetch('../../../../../src/main/resources/data/digestion.json')
    .then(response => response.json())
    .then(data => {
        // 将JSON数据转换为数组对象
        const jsonArray = JSON.parse(data);
        console.log(jsonArray);

        // 创建一个ECharts实例
        const digestionChart = echarts.init(document.getElementById('digestionContainer'));

        // 提取蛋白质名称和肽段数量作为坐标数据
        const proteinNames = jsonArray.map(item => item.proteinName);
        const peptideCounts = jsonArray.map(item => item.peptideCount);

        // 设置图表的选项
        digestionChart.setOption({
            title: {
                text: '蛋白质肽段数量散点图'
            },
            xAxis: {
                name: "蛋白质",
                type: 'category',
                data: proteinNames,
                axisLabel: {
                    rotate: 45, // 旋转x轴标签45度
                    interval: 0, // 显示所有标签
                    formatter: function (value) {
                        // 自定义格式化函数，防止标签截断
                        return value.length > 6 ? value.substring(0, 6) + '...' : value;
                    },
                    textStyle: {
                        fontFamily: 'Arial, sans-serif' // 设置字体样式，确保中文显示正常
                    }
                }
            },
            yAxis: {
                name: "肽段数量"
            },
            series: [{
                symbolSize: 10,
                type: 'scatter',
                data: peptideCounts
            }]
        });

        // 调用 resize 方法使图表适应容器尺寸
        digestionChart.resize();
    })
    .catch(error => console.error(error));

