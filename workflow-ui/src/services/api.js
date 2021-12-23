import request from '@/utils/request';

export async function qryTemplate(params) {
  return request('/api/t1/template/qryTemplate', {
    method: 'POST',
    data: {
      ...params
    }
  })
}

export async function addTemplate(params) {
  return request('/api/t1/template/addTemplate', {
    method: 'POST',
    data: {
      ...params
    }
  });
}

export async function delTemplate(params) {
  return request('/api/t1/template/delTemplate', {
    method: 'POST',
    data: {
      ...params
    }
  })
}

export async function updateTemplate(params) {
  return request('/api/t1/template/updateTemplate', {
    method: 'POST',
    data: {
      ...params
    }
  })
}