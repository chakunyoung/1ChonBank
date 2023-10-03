import apis from 'services/api/apis';

export const getDepositorByNickname = async (data) => {
  const response = await apis.get("/api/depositor/Custommer/" + data);
  return response.data;
};

export const getLoanerByNickname = async (data) => {
  const familys = await apis.get("/api/loaner/Custommer/" + data);
  return familys.data;
};

export const getSavingserByNickname = async (data) => {
  const response = await apis.get("/api/savingser/Custommer/" + data);
  return response.data;
};
