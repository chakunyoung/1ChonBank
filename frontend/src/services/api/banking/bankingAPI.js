import apis from 'services/api/apis';

// 예금 목록 조회
export const getDepositors = async () => {
  const response = await apis.get('/api/banking/depositor');
  return response.data;
};

// 대출 목록 조회
export const getLoaners = async () => {
  const response = await apis.get('/api/banking/loaner');
  return response.data;
};

// 적금 목록 조회
export const getSavings = async () => {
  const response = await apis.get('/api/banking/saving');
  return response.data;
};
