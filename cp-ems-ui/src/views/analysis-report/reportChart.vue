<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts'
// require('echarts/theme/macarons') // echarts theme
import resize from '../dashboard/mixins/resize'

const animationDuration = 1500

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '300px'
    },
    yName: {
      type: String,
      default: 'kM·h'
    },
    xName: {
      type: String,
      default: '时'
    },
    title: {
      type: String,
      default: '用电量'
    },
    xData: {
      type: Array,
      default: () => {
        return ['00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13']
      }
    },
    yData: {
      type: Array,
      default: () => {
        return [79, 52, 200, 334, 390, 330, 220, 0, 21, 67, 234, 21, 89, 56]
      }
    },
  },
  data() {
    return {
      chart: null
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  watch: {
    'xData': {
      handler() {
        this.$nextTick(() => {
          if (!this.chart) {
            this.initChart();
            return;
          }
          this.chart.dispose();
          this.chart = null;
          this.initChart();
        });
      }
    }
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el)
      // this.chart = echarts.init(this.$el, 'macarons')

      this.chart.setOption({
        title:{
          left:'center',
          text:this.title,
          fontSize: 18,
          fontWeight: 600,
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        grid: {
          top: '15%',
          left: '2%',
          right: '7%',
          bottom: '3%',
          containLabel: true
        },
        dataZoom: {
          show: true,
          type: 'inside'
        },
        xAxis: [{
          type: 'category',
          data: this.xData,
          name: this.xName,
          axisTick: {
            alignWithLabel: true
          }
        }],
        yAxis: [{
          // type: 'value',
          // name: 'kmh',
          // axisTick: {
          //   show: true
          // }
          type: 'value',
          name: this.yName,
          position: 'left',
          alignTicks: true,
        }],
        series: [{
          // name: 'pageA',
          type: 'bar',
          // stack: 'vistors',
          barWidth: '70%',
          data: this.yData,
          animationDuration,
          itemStyle: {
            color: '',
          },
          markPoint: {
            data: [
              { type: 'max', name: 'Max' },
              { type: 'min', name: 'Min' }
            ]
          },
        }]
      })
    }
  }
}
</script>
