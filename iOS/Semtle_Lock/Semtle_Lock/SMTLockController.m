//
//  SMTLockController.m
//  Semtle_Lock
//
//  Created by MB KWON on 2013. 12. 5..
//  Copyright (c) 2013년 Semtle_men. All rights reserved.
//

#import "SMTLockController.h"
#import "NSData+AES256.h"

@implementation SMTLockController


-(NSInteger)setPassword:(NSString *)password
{
    if (_key == nil) {
        
        return SMT_LOCK_NOT_KEY;
        
    } else {
        
        NSData *rowData = [password dataUsingEncoding:NSUTF8StringEncoding];
        NSData *encryptedData = [rowData encryptAES256WithKey:_key];
        
        if (encryptedData == nil) {
            
            return SMT_LOCK_ENCRYPTION_FAIL;
        
        } else {
            
            BOOL result = [encryptedData writeToFile:@"SMT_ITEM_PASS" atomically:YES];
            
            if (result == YES) {
                return SMT_LOCK_OK;
            } else {
                return SMT_LOCK_FILESAVE_FAIL;
            }
        }
    }
}

@end
