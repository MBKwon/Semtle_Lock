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

-(SMT_RESULT_CODE)savePass:(NSData *)encryptedData
{
    
    NSString *fullPath = [DEFAULT_PATH  stringByAppendingString:@"/"];
    fullPath = [fullPath stringByAppendingString:FILE_NAME];
    
    BOOL result = [encryptedData writeToFile:FILE_NAME atomically:YES];
    
    if (result == YES) {
        return SMT_LOCK_OK;
    } else {
        return SMT_LOCK_FILESAVE_FAIL;
    }
}

-(SMT_RESULT_CODE)loadPass:(NSData *)encryptedData
{
    
    NSString *fullPath = [DEFAULT_PATH  stringByAppendingString:@"/"];
    fullPath = [fullPath stringByAppendingString:FILE_NAME];
    
    
    BOOL result = [[NSFileManager defaultManager] fileExistsAtPath:fullPath];
    if (!result) {
        
        return SMT_LOCK_FILELOAD_FAIL;
        
    } else {
        
        encryptedData = [NSData dataWithContentsOfFile:fullPath];
        return SMT_LOCK_OK;
    }
}



-(SMT_RESULT_CODE)setPassword:(NSString *)password
{
    if (_key == nil) {
        
        return SMT_LOCK_NOT_KEY;
        
    } else {
        
        NSData *rowData = [password dataUsingEncoding:NSUTF8StringEncoding];
        NSData *encryptedData = [rowData encryptAES256WithKey:_key];
        
        if (encryptedData == nil) {
            
            return SMT_LOCK_ENCRYPTION_FAIL;
            
        } else {
            
            SMT_RESULT_CODE result = [self savePass:encryptedData];
            
            return result;
        }
    }
}

-(SMT_RESULT_CODE)matchPassword:(NSString *)password
{
    if (_key == nil) {
        
        return SMT_LOCK_NOT_KEY;
        
    } else {
        
        NSData *rowData = [password dataUsingEncoding:NSUTF8StringEncoding];
        NSData *encryptedData = [rowData encryptAES256WithKey:_key];
        
        if (encryptedData == nil) {
            
            return SMT_LOCK_ENCRYPTION_FAIL;
            
        } else {
            
            NSData *savedPassData = [NSData new];
            SMT_RESULT_CODE result = [self loadPass:savedPassData];
            
            if (result == SMT_LOCK_OK) {
                
                if ([encryptedData isEqualToData:savedPassData] == YES) {
                    return result;
                } else {
                    return SMT_LOCK_PASS_NOT_MATCH;
                }
                
            } else {
                return result;
            }
        }
    }
}


@end
