// 基于准备好的容器，初始化echarts实例
const chart1 = echarts.init(document.getElementById('chartContainer1'));
const chart2 = echarts.init(document.getElementById('chartContainer2'));
//指定图表的配置项和数据
const option1 = {
    title: {
        text: '柱状图'
    },
    legend: {
        data: ['Zlib', 'Zstd', 'Snappy', 'LZMA']
    },
    xAxis: {
        data: ['fasta', 'gff', 'tsv']
    },
    yAxis: {},
    series: [
        {
            name: 'Zlib',
            type: 'bar',
            data: [63.2, 90, 80.6]
        },
        {
            name: 'Zstd',
            type: 'bar',
            data: [58.5, 85.1, 75.6]
        },
        {
            name: 'Snappy',
            type: 'bar',
            data: [27.4, 77.7, 63.6]
        },
        {
            name: 'LZMA',
            type: 'bar',
            data: [80.7, 93.5, 89.3]
        }
    ]
};
const option2 = {
    title: {
        text: '折线图'
    },
    legend: {
        data: ['fasta', 'gff', 'tsv']
    },
    xAxis: {
        type: 'category',
        data: ['Zlib', 'Zstd', 'Snappy', 'LZMA']
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            name:'fasta',
            data: [63.2, 58.5, 27.4, 80.7],
            type: 'line'
        },
        {
            name: 'gff',
            type: 'line',
            data: [90, 85.1, 77.7, 93.5]
        },
        {
            name: 'tsv',
            type: 'line',
            data: [80.6, 75.6, 63.6, 89.3]
        }
    ]
};
// 使用刚指定的配置项和数据显示图表。
chart1.setOption(option1);
chart2.setOption(option2);