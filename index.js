import {NativeModules} from 'react-native';

const {RaonSecure} = NativeModules;

export const clearTempDir = async () => {
    return await RaonSecure.clearTempDir();
}

export const getItems = async () => {
    return await RaonSecure.getItems();
}

export const removeItem = async (subjectDn) => {
    return await RaonSecure.removeItem(subjectDn);
}

export const getPath = async (subjectDn) => {
    return await RaonSecure.getPath(subjectDn);
}

export const checkPassword = async (subjectDn, password) => {
    return await RaonSecure.checkPassword(subjectDn, password);
}

export const getReceiveCode = async () => {
    return await RaonSecure.getReceiveCode();
}

export const importCertify = async () => {
    return await RaonSecure.importCertify();
}


export default {
    clearTempDir,
    getItems,
    removeItem,
    getPath,
    checkPassword,
    getReceiveCode,
    importCertify
};
