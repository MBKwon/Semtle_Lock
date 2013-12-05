//
//  NSData+AES256.h
//  MBMemo
//
//  Created by MB KWON on 2013. 12. 4..
//  Copyright (c) 2013년 UANGEL Corp. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSData (AES256)

-(NSData *)encryptAES256WithKey:(NSString *)key;
-(NSData *)decryptAES256WithKey:(NSString *)key;

@end
