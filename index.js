import {NativeModules} from 'react-native';

const {RNRaonSecure} = NativeModules;

export const clearTempDir = async () => {
    return await RNRaonSecure.clearTempDir();
}

export const getItems = async () => {
    return await RNRaonSecure.getItems();
}

export const removeItem = async (subjectDn) => {
    return await RNRaonSecure.removeItem(subjectDn);
}

export const getPath = async (subjectDn) => {
    return await RNRaonSecure.getPath(subjectDn);
}

export const checkPassword = async (subjectDn, password) => {
    return await RNRaonSecure.checkPassword(subjectDn, password);
}

export const getReceiveCode = async () => {
    return await RNRaonSecure.getReceiveCode();
}

export const importCertify = async () => {
    return await RNRaonSecure.importCertify();
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
