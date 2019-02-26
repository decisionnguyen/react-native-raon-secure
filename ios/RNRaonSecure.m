
#import "RNRaonSecure.h"
#import "RSKSW/RSKSWCertManager.h"
#import "RSKSW/RSKSWICRProtocol.h"

@implementation RNRaonSecure

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE()


RCT_REMAP_METHOD(getItems,
                 getItemsWithResolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    @try {
        RSKSWCertManager *manager = [RSKSWCertManager getInstance];
        NSDictionary * result = @{ @"code": @"" };
        resolve(result);
    }
    @catch(NSException * e) {
        NSLog(@"%@", e);
        NSLog(@"ERRORR....");
        reject(@"RNRaonSecure", e.userInfo.description, nil);
    }
}

RCT_REMAP_METHOD(removeItem,
                 removeItemWithParam:(nonnull NSDictionary *)params resolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    @try {
        RSKSWCertManager *manager = [RSKSWCertManager getInstance];
        NSDictionary * result = @{ @"code": @"" };
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
        
        //        RSKSWCertManager *manager = [RSKSWCertManager getInstance];
        //        resolve([self getAccessToken]);
        
        RSKSWICRProtocol *icrp = [[RSKSWICRProtocol alloc] initWithIP:@"ksbiz.raonsecure.com" port:8080 siteCode:@"00000"];
        
        int ret = [icrp import1];
        if (ret < 0) {
            //            NSLog(@"%d : %@", icrp.lastErrorCode, icrp.lastErrorMessage);
            reject(@"RNRaonSecure", icrp.lastErrorMessage, nil);
        }
        else {
            NSDictionary * result = @{ @"code": [icrp generatedNumber] };
            resolve(result);
        }
        
        
    }
    @catch(NSException * e) {
        NSLog(@"%@", e);
        NSLog(@"ERRORR....");
        reject(@"RNRaonSecure", e.userInfo.description, nil);
    }
}


RCT_REMAP_METHOD(importCertify,
                 importCertifyWithResolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    
    @try {
        RSKSWCertManager *manager = [RSKSWCertManager getInstance];
        NSDictionary * result = @{ @"code": @"" };
        resolve(result);
    }
    @catch(NSException * e) {
        NSLog(@"%@", e);
        NSLog(@"ERRORR....");
        reject(@"RNRaonSecure", e.userInfo.description, nil);
    }
}


@end

