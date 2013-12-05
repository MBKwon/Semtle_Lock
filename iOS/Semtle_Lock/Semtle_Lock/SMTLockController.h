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
#define SMT_LOCK_NOT_KEY                        0x00000001
#define SMT_LOCK_ENCRYPTION_FAIL                0x00000002
#define SMT_LOCK_FILESAVE_FAIL                  0x00000003


@interface SMTLockController : NSObject

@property (nonatomic, strong) NSString *key;

-(NSInteger)setPassword:(NSString *)password;

@end
