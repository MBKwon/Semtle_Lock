//
//  SMTLockController.h
//  Semtle_Lock
//
//  Created by MB KWON on 2013. 12. 5..
//  Copyright (c) 2013년 Semtle_men. All rights reserved.
//

#import <Foundation/Foundation.h>

//ERROR_DEFINE
#define SMT_LOCK_OK                             0x00000000

#define SMT_LOCK_NOT_KEY                        0xff000001
#define SMT_LOCK_NOT_PASSWORD                   0xff000002
#define SMT_LOCK_ENCRYPTION_FAIL                0xff000003
#define SMT_LOCK_FILESAVE_FAIL                  0xff000004
#define SMT_LOCK_FILELOAD_FAIL                  0xff000005
#define SMT_LOCK_NOT_MATCHED                    0xff000006


@interface SMTLockController : NSObject

@property (nonatomic, strong) NSString *key;


-(SMT_RESULT_CODE)isSetPass;
-(SMT_RESULT_CODE)setPassword:(NSString *)password;
-(SMT_RESULT_CODE)matchPassword:(NSString *)password;

@end
