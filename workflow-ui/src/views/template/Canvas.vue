<template>
  <q-dialog v-model="showTemplateDialog">
    <q-layout style="width: 1000px; max-width: 200vw;" view="Lhh lpR fff" container class="bg-white">
      <q-card>
        <div style="width:100%" class="container q-pa-md row q-col-gutter-sm">
          <div class="col-12 col-sm-3">
            <q-input v-model="templateModel.name" label="模版名称" :rules="[ val => val && val.length > 0 || '请输入模版名称']" />
            <div class="text-primary">添加节点: 双击空白画布</div>
            <div class="text-primary">修改节点: 双击节点</div>
            <div class="text-primary">删除节点: Del</div>
          </div>
          <div class="col-12 col-sm-9 q-gutter-sm">
            <div class="text-h6 text-primary">审批流程</div>
            <div>
              <q-scroll-area :thumb-style="thumbStyle" :bar-style="barStyle" style="height: 550px; max-width: 100%;">
                <flowchart :nodes="nodes" :connections="connections" @editnode="handleEditNode" :width="'100%'" :height="900" :readonly="false" @dblclick="handleDblClick" @editconnection="handleEditConnection" @save="handleChartSave" @select="handleSelect" @selectconnection="handleSelectConnection" ref="chart" :render="render">
                </flowchart>
              </q-scroll-area>
            </div>
          </div>
          <!-- <div id="toolbar"> -->
          <!-- <button @click="
          $refs.chart.add({
            id: +new Date(),
            x: 10,
            y: 10,
            name: 'New',
            type: 'operation',
            approvers: [],
          })
        ">
        Add(Double-click canvas)
      </button> -->
          <!-- <button @click="$refs.chart.remove()">Delete(Del)</button>
      <button @click="$refs.chart.editCurrent()">
        Edit(Double-click node)
      </button>
      <button @click="$refs.chart.save()">Save</button> -->
          <!-- </div> -->

          <node-dialog :visible.sync="nodeDialogVisible" :node.sync="nodeForm.target"></node-dialog>
          <connection-dialog :visible.sync="connectionDialogVisible" :connection.sync="connectionForm.target" :operation="connectionForm.operation">
          </connection-dialog>
        </div>
      </q-card>
      <q-footer class="bg-white text-white">
        <q-toolbar inset>
          <q-toolbar-title class="text-right">
            <q-btn @click="$refs.chart.save()" label="保存" color="primary" />
          </q-toolbar-title>
        </q-toolbar>
      </q-footer>
    </q-layout>
  </q-dialog>
</template>
<script>
/* eslint-disable no-unused-vars */

import ConnectionDialog from '@/components/flowchart/ConnectionDialog'
import NodeDialog from '@/components/flowchart/NodeDialog'
import Flowchart from '@/components/flowchart/Flowchart'
import * as d3 from 'd3'
import { roundTo20 } from '@/components/flowchart/utils/math'

import { addTemplate, updateTemplate } from '@/services/api'
import { positiveNotify, warningNotify } from '@/utils/utils'
import { templateModel } from '@/model'

export default {
  components: {
    ConnectionDialog,
    NodeDialog,
    Flowchart,
  },
  data() {
    return {
      showTemplateDialog: false,
      thumbStyle: {
        right: '4px',
        borderRadius: '5px',
        backgroundColor: '#027be3',
        width: '5px',
        opacity: 0.75,
      },
      barStyle: {
        right: '2px',
        borderRadius: '9px',
        backgroundColor: '#027be3',
        width: '9px',
        opacity: 0.2,
      },
      templateModel: templateModel(),
      nodes: [],
      defaultNodes: [
        { id: +new Date() + 1, x: 50, y: 150, name: 'Start', type: 'start' },
        { id: +new Date() + 2, x: 550, y: 150, name: 'End', type: 'end' },
        // {
        //   id: 3,
        //   x: 340,
        //   y: 130,
        //   name: 'Custom size',
        //   type: 'operation',
        //   approvers: [{ id: 1, name: 'Joyce' }],
        //   width: 120,
        //   height: 40,
        // },
        // {
        //   id: 4,
        //   x: 240,
        //   y: 220,
        //   name: 'Operation',
        //   type: 'operation',
        //   approvers: [{ id: 2, name: 'Allen' }],
        // },
        // {
        //   id: 5,
        //   x: 440,
        //   y: 220,
        //   name: 'Operation',
        //   type: 'operation',
        //   approvers: [{ id: 3, name: 'Teresa' }],
        // },
      ],
      connections: [
        // {
        //   source: { id: 1, position: 'right' },
        //   destination: { id: 4, position: 'left' },
        //   id: 1,
        //   type: 'pass',
        // },
        // {
        //   source: { id: 4, position: 'right' },
        //   destination: { id: 5, position: 'left' },
        //   id: 2,
        //   type: 'pass',
        // },
        // {
        //   source: { id: 5, position: 'right' },
        //   destination: { id: 2, position: 'left' },
        //   id: 3,
        //   type: 'pass',
        // },
        // {
        //   source: { id: 5, position: 'bottom' },
        //   destination: { id: 4, position: 'bottom' },
        //   id: 4,
        //   type: 'reject',
        // },
        // {
        //   source: { id: 1, position: 'top' },
        //   destination: { id: 3, position: 'left' },
        //   id: 5,
        //   type: 'pass',
        // },
        // {
        //   source: { id: 3, position: 'right' },
        //   destination: { id: 2, position: 'top' },
        //   id: 6,
        //   type: 'pass',
        // },
      ],
      nodeForm: { target: null },
      connectionForm: { target: null, operation: null },
      nodeDialogVisible: false,
      connectionDialogVisible: false,
      addFlag: true,
    }
  },
  async mounted() {},
  methods: {
    handleDblClick(position) {
      this.$refs.chart.add({
        id: +new Date(),
        x: position.x,
        y: position.y,
        name: 'New',
        type: 'operation',
        approvers: [],
      })
    },
    handleSelect(nodes) {
      // console.log(nodes);
    },
    handleSelectConnection(connections) {
      // console.log(connections);
    },
    showAddTemplateDialog() {
      this.showTemplateDialog = true
      this.nodes = this.defaultNodes
      this.connections = []
      this.templateModel = templateModel()
      this.addFlag = true
    },
    async handleChartSave(nodes, connections) {
      if (this.templateModel.name === null || this.templateModel.name === '') {
        warningNotify('请输入模版名称')
        return
      }
      if (this.addFlag === true) {
        const re = await addTemplate({
          name: this.templateModel.name,
          type: '',
          entId: 1,
          nodes: nodes,
          connections: connections,
        })
        this.$parent.addSuccess(re)
        this.showTemplateDialog = false
        positiveNotify('添加成功!')
      } else {
        //更新模版
        const re = await updateTemplate({
          templateId: this.templateModel.id,
          name: this.templateModel.name,
          type: '',
          entId: 1,
          nodes: nodes,
          connections: connections,
        })
        this.$parent.updateSuccess(re)
        this.showTemplateDialog = false
        positiveNotify('更新成功!')
      }
    },
    showTemplateInfo(template) {
      this.templateModel = { ...template }
      this.nodes = template.nodes
      this.connections = template.connections
      this.addFlag = false
      this.showTemplateDialog = true
    },
    handleEditNode(node) {
      this.nodeForm.target = node
      this.nodeDialogVisible = true
    },
    handleEditConnection(connection) {
      this.connectionForm.target = connection
      this.connectionDialogVisible = true
    },
    render: function (g, node, isSelected) {
      node.width = node.width || 120
      node.height = node.height || 60
      let borderColor = isSelected ? '#666666' : '#bbbbbb'
      if (node.type !== 'start' && node.type !== 'end') {
        // title
        if (node.id !== 3) {
          g.append('rect')
            .attr('x', node.x)
            .attr('y', node.y)
            .attr('stroke', borderColor)
            .attr('class', 'title')
            .style('height', '20px')
            .style('fill', '#f1f3f4')
            .style('stroke-width', '1px')
            .style('width', node.width + 'px')
          g.append('text')
            .attr('x', node.x + 4)
            .attr('y', node.y + 15)
            .attr('class', 'unselectable')
            .text(() => node.name)
            .each(function wrap() {
              let self = d3.select(this),
                textLength = self.node().getComputedTextLength(),
                text = self.text()
              while (textLength > node.width - 2 * 4 && text.length > 0) {
                text = text.slice(0, -1)
                self.text(text + '...')
                textLength = self.node().getComputedTextLength()
              }
            })
        }
      }
      // body
      if (node.id === 3) {
        let body = g.append('ellipse').attr('class', 'body')
        body.attr('cx', node.x + node.width / 2)
        body.attr('cy', node.y + node.height / 2)
        body.attr('rx', node.width / 2)
        body.attr('ry', node.height / 2)
        body.style('fill', 'white')
        body.style('stroke-width', '1px')
        body.classed(node.type, true)
        body.attr('stroke', borderColor)
      } else {
        let body = g.append('rect').attr('class', 'body')
        body
          .style('width', node.width + 'px')
          .style('fill', 'white')
          .style('stroke-width', '1px')
        if (node.type !== 'start' && node.type !== 'end') {
          body.attr('x', node.x).attr('y', node.y + 20)
          body.style('height', roundTo20(node.height - 20) + 'px')
        } else {
          body.attr('x', node.x).attr('y', node.y).classed(node.type, true).attr('rx', 30)
          body.style('height', roundTo20(node.height) + 'px')
        }
        body.attr('stroke', borderColor)
      }

      // body text
      let text =
        node.type === 'start'
          ? 'Start'
          : node.type === 'end'
          ? 'End'
          : !node.approvers || node.approvers.length === 0
          ? 'No approver'
          : node.approvers.length > 1
          ? `${node.approvers[0].name + '...'}`
          : node.approvers[0].name
      let bodyTextY
      if (node.type !== 'start' && node.type !== 'end') {
        if (node.id === 3) {
          bodyTextY = node.y + 25
        } else {
          bodyTextY = node.y + 25 + roundTo20(node.height - 20) / 2
        }
      } else {
        bodyTextY = node.y + 5 + roundTo20(node.height) / 2
      }
      g.append('text')
        .attr('x', node.x + node.width / 2)
        .attr('y', bodyTextY)
        .attr('class', 'unselectable')
        .attr('text-anchor', 'middle')
        .text(function () {
          return text
        })
        .each(function wrap() {
          let self = d3.select(this),
            textLength = self.node().getComputedTextLength(),
            text = self.text()
          while (textLength > node.width - 2 * 4 && text.length > 0) {
            text = text.slice(0, -1)
            self.text(text + '...')
            textLength = self.node().getComputedTextLength()
          }
        })
    },
  },
  async created() {},
}
</script>
<style scoped>
#toolbar {
  margin-bottom: 10px;
}

.title {
  margin-top: 10px;
  margin-bottom: 0;
}

.subtitle {
  margin-bottom: 10px;
}

#toolbar > button {
  margin-right: 4px;
}

.container {
  width: 800px;
  margin: auto;
}
</style>
