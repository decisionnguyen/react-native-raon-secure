
#import "RNRaonSecure.h"
#import "RSKSW/RSKSWCertManager.h"

@implementation RNRaonSecure

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE()


RCT_REMAP_METHOD(getItems,
                 getItemsWithResolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    @try {
        NSMutableArray* rows = [[NSMutableArray alloc] init];
        RSKSWCertManager *manager = [RSKSWCertManager getInstance];
        
        if ([manager count] > 0) {
            for (int i = 0; i < [manager count]; i++) {
                RSKSWCertificate* cert = [manager getCert:i];
                NSDictionary * result = @{
                                          @"subjectDn": cert.getSubjectDn,
                                          @"subjectCn": cert.getSubjectCn,
                                          @"policy": cert.getPolicy,
                                          @"notAfterDate": cert.getNotAfterDate,
                                          };
                [rows addObject: result];
            }
        }
        resolve(rows);
    }
    @catch(NSException * e) {
        NSLog(@"%@", e);
        NSLog(@"ERRORR....");
        reject(@"RNRaonSecure", e.userInfo.description, nil);
    }
}

RCT_REMAP_METHOD(removeItem,
                 removeItemWithSubjectDn:(nonnull NSString *)subjectDn resolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    @try {
        RSKSWCertManager *manager = [RSKSWCertManager getInstance];
        int targetIdx = -1;
        if ([manager count] > 0) {
            for (int i = 0; i < [manager count]; i++) {
                RSKSWCertificate* cert = [manager getCert:i];
                if ([cert.getSubjectDn isEqualToString:subjectDn]) {
                    targetIdx = i;
                    break;
                }
            }
            if (targetIdx >= 0) {
                [manager delCert: targetIdx];
            }
        }
        
        NSDictionary * result = @{ @"removeTo": [NSNumber numberWithInt: targetIdx] };
        resolve(result);
    }
    @catch(NSException * e) {
        NSLog(@"%@", e);
        NSLog(@"ERRORR....");
        reject(@"RNRaonSecure", e.userInfo.description, nil);
    }
}


RCT_REMAP_METHOD(clear,
                 clearWithResolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    @try {
        RSKSWCertManager *manager = [RSKSWCertManager getInstance];
        [manager delAllCert: RSKSWConstCertMode_DEFAULT];
        
        NSDictionary * result = @{ @"success": @"true" };
        resolve(result);
    }
    @catch(NSException * e) {
        NSLog(@"%@", e);
        NSLog(@"ERRORR....");
        reject(@"RNRaonSecure", e.userInfo.description, nil);
    }
}


RCT_REMAP_METHOD(exportFile,
                 exportFileWithSubjectDn:(nonnull NSString *)subjectDn Path:(nonnull NSString *)path resolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    @try {
        RSKSWCertManager *manager = [RSKSWCertManager getInstance];
        
        NSDictionary * result = @{ @"success": @"true" };
        resolve(result);
    }
    @catch(NSException * e) {
        NSLog(@"%@", e);
        NSLog(@"ERRORR....");
        reject(@"RNRaonSecure", e.userInfo.description, nil);
    }
}


RCT_REMAP_METHOD(checkPassword,
                 checkPasswordWithSubjectDn:(nonnull NSString *)subjectDn Password:(nonnull NSString *)password resolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    @try {
        RSKSWCertManager *manager = [RSKSWCertManager getInstance];
        
        NSDictionary * result = @{ @"success": @"true" };
        resolve(result);
    }
    @catch(NSException * e) {
        NSLog(@"%@", e);
        NSLog(@"ERRORR....");
        reject(@"RNRaonSecure", e.userInfo.description, nil);
    }
}


RCT_REMAP_METHOD(getReceiveCode,
                 getReceiveCodeWithResolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    @try {
        if (icrp == nil) {
            int ret1 = [RSKSWICRProtocol getLibraryState];
            if (ret1 != 8) {
                reject(@"RNRaonSecure", @"License Not Allowed", nil);
                return;
            }
            
            icrp = [[RSKSWICRProtocol alloc] initWithIP:@"211.32.131.182" port:10500];
        }
        int ret = [icrp import1];
        if (ret < 0) {
            reject(@"RNRaonSecure", icrp.lastErrorMessage, nil);
        }
        else {
            NSDictionary * result = @{ @"code": [icrp generatedNumber] };
            resolve(result);
        }
    }
    @catch(NSException * e) {
        reject(@"RNRaonSecure", e.userInfo.description, nil);
    }
}


RCT_REMAP_METHOD(importCertify,
                 importCertifyWithResolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    
    @try {
        int ret = [icrp import2];
        BOOL bRet = FALSE;
        RSKSWCertificate *cert = nil;
        RSKSWCertManager *manager = [RSKSWCertManager getInstance];
        
        if (ret == 1) {
            NSData *importedCert = icrp.certData;
            NSData *importedKey = icrp.keyData;
            
            bRet = [manager saveCert:importedCert key:importedKey];
            if (!bRet) {
                NSDictionary * result = @{
                                          @"isError": @"Y",
                                          @"type": @"B",
                                          @"ret": [NSNumber numberWithInt:bRet],
                                          };
                resolve(result);
                return;
            }
            
            if (icrp.kmCertData != nil && icrp.kmKeyData!= nil) {
                if(![manager saveCert:icrp.kmCertData key:icrp.kmKeyData]){
                    reject(@"RNRaonSecure", @"Error - saveCert: key:", nil);
                    //                    RCTLogInfo(@"인증서 저장 실패");
                }
            }
            cert = [[RSKSWCertificate alloc] initWithCert:importedCert];
            NSDictionary * result = @{
                                      @"subjectDn": cert.getSubjectDn,
                                      @"subjectCn": cert.getSubjectCn,
                                      @"policy": cert.getPolicy,
                                      @"notAfterDate": cert.getNotAfterDate,
                                      };
            resolve(result);
        }
        else {
            NSDictionary * result = @{
                                      @"isError": @"Y",
                                      @"type": @"A",
                                      @"ret": [NSNumber numberWithInt:ret],
                                      @"message": icrp.lastErrorMessage,
                                      };
            resolve(result);
        }
    }
    @catch(NSException * e) {
        NSLog(@"%@", e);
        NSLog(@"ERRORR....");
        reject(@"RNRaonSecure", e.userInfo.description, nil);
    }
}


@end

