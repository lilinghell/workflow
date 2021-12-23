<template>
  <div class="q-pa-md">
    <q-splitter separator-class="bg-white" v-model="splitterModel" style="height: 100%; min-height: 600px;">
      <template v-slot:before>
        <div class="q-pa-md">
          <div class="doc-heading doc-h1">模版列表</div>
          <div class="q-my-md">
            <q-table flat :data="templateList" :columns="columns" :filter="filter" row-key="index" :pagination.sync="pagination" hide-pagination>
              <template v-slot:top-left>
                <q-input borderless dense debounce="300" v-model="filter" placeholder="Search">
                  <template v-slot:append>
                    <q-icon name="search" color="primary" />
                  </template>
                </q-input>
              </template>
              <template v-slot:top-right>
                <q-btn color="primary" unelevated class="table-head-btn" @click="addShow">
                  新增
                  <q-icon name="add" class="q-ml-sm" />
                </q-btn>
              </template>
              <template v-slot:body-cell-operation="props">
                <q-td :class="props.col.__tdClass" class="q-gutter-x-sm">
                  <q-btn flat color="primary" label="详情" @click="showTemplate(props.row)" />
                  <q-btn flat color="primary" label="删除" @click="delTemplate(props.row)" />
                </q-td>
              </template>
            </q-table>
            <div class="q-pa-lg flex flex-center">
              <q-pagination color="primary" v-model="pagination.page" :max="pagesNumber" :direction-links="true">
              </q-pagination>
            </div>
          </div>
        </div>
      </template>
      <template v-slot:after>
        <div class="q-pa-md">
        </div>
      </template>
    </q-splitter>

    <Canvas ref="Canvas" />

    <q-dialog v-model="delTemplateConfirm" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <q-avatar icon="delete" color="primary" text-color="white" />
          <span class="q-ml-sm" style="padding-left:30px;width: 200px">
            是否确定删除？
          </span>
        </q-card-section>
        <q-card-actions align="right">
          <q-btn flat label="取消" color="primary" v-close-popup />
          <q-btn flat label="确定" color="primary" @click="delConfirmTemplate()" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>
<script>
import { positiveNotify } from '@/utils/utils'
import Canvas from '@/views/template/Canvas.vue'
import { qryTemplate, delTemplate } from '@/services/api'
export default {
  name: 'TemplateList',
  components: {
    Canvas,
  },
  data() {
    return {
      delTemplateConfirm: false,
      splitterModel: 90,
      templateList: [],
      columns: [
        {
          name: 'name',
          label: '模版名称',
          align: 'center',
          field: (row) => row.name,
          style: 'width: 40%',
        },
        {
          name: 'time',
          label: '更新时间',
          align: 'center',
          field: (row) => row.updateTime,
          style: 'width: 30%',
        },
        {
          name: 'operation',
          align: 'center',
          label: '操作',
          field: (row) => row,
          style: 'width: 30%',
        },
      ],
      filter: '',
      pagination: {
        sortBy: 'desc',
        descending: false,
        page: 0,
        rowsPerPage: 5,
      },
    }
  },
  computed: {
    pagesNumber() {
      return Math.ceil(this.templateList.length / this.pagination.rowsPerPage)
    },
  },
  methods: {
    async delConfirmTemplate() {
      await delTemplate({
        templateId: this.delTemplateModel.id,
        entId: 1,
      })
      this.templateList = this.templateList.filter(
        (template) => template.id != this.delTemplateModel.id
      )
      this.delTemplateConfirm = false
      positiveNotify('删除成功!')
    },
    addShow() {
      this.$refs.Canvas.showAddTemplateDialog()
    },
    showTemplate(row) {
      this.$refs.Canvas.showTemplateInfo(row)
    },
    async delTemplate(row) {
      this.delTemplateModel = row
      this.delTemplateConfirm = true
    },
    addSuccess(template) {
      this.templateList.unshift(template)
    },
    updateSuccess(reTemplate) {
      console.log('nihao')
      this.templateList = this.templateList.map((template) => {
        if (template.id.toString() === reTemplate.id.toString()) {
          return reTemplate
        } else {
          return template
        }
      })
    },
  },
  async created() {
    const re = await qryTemplate({
      entId: 1,
    })
    this.templateList = re.templates
    // re.templates.forEach((re) => {
    //   this.nodes = re.nodes
    //   this.connections = re.connections
    // })
  },
}
</script>
<style lang="sass" scoped>
@import "~@/styles/quasar.scss"
.doc-heading
  color: $primary
  // cursor: pointer
.doc-h1
  font-size: 1.5rem
  line-height: 2rem
  font-weight: 500
</style>
