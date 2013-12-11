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

-(id)init
{
    self = [super init];
    if (self) {
        
        _key = @"PASSWORD_KEY";
    }
    
    return self;
}


#pragma mark - internal methods
-(SMT_RESULT_CODE)savePass:(NSData *)encryptedData
{
    @try {
        
        [[NSUserDefaults standardUserDefaults] setValue:encryptedData forKey:KEY_NAME];
        [[NSUserDefaults standardUserDefaults] synchronize];
        return SMT_LOCK_OK;
    }
    @catch (NSException *exception) {
        
        NSLog(@"%@", exception.description);
        return SMT_LOCK_FILESAVE_FAIL;
    }
}

-(SMT_RESULT_CODE)loadPass:(NSData **)encryptedData
{
    @try {
        
        *encryptedData = [[NSUserDefaults standardUserDefaults] dataForKey:KEY_NAME];
        return SMT_LOCK_OK;
    }
    @catch (NSException *exception) {
        
        NSLog(@"%@", exception.description);
        return SMT_LOCK_FILELOAD_FAIL;
    }
}


#pragma mark - public methods
-(SMT_RESULT_CODE)isSetPass
{
    @try {
        
        NSData *encryptedData = [[NSUserDefaults standardUserDefaults] dataForKey:KEY_NAME];
        if (encryptedData == nil) {
            
            return SMT_LOCK_NOT_PASSWORD;
            
        } else {
            return SMT_LOCK_OK;
        }
    }
    @catch (NSException *exception) {
        
        NSLog(@"%@", exception.description);
        return SMT_LOCK_NOT_PASSWORD;
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
            password = nil;
            rowData = nil;
            encryptedData = nil;
            
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
            SMT_RESULT_CODE result = [self loadPass:&savedPassData];
            
            if (result == SMT_LOCK_OK) {
                
                if ([encryptedData isEqualToData:savedPassData] == YES) {
                    
                    return result;
                    
                } else {
                    
                    return SMT_LOCK_NOT_MATCHED;
                }
                
            } else {
                return result;
            }
        }
    }
}


@end
