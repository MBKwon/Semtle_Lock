//
//  NSData+AES256.m
//  MBMemo
//
//  Created by MB KWON on 2013. 12. 4..
//  Copyright (c) 2013년 UANGEL Corp. All rights reserved.
//

#import <CommonCrypto/CommonCryptor.h>
#import "NSData+AES256.h"

@implementation NSData (AES256)


-(NSData *)encryptAES256WithKey:(NSString *)key
{
    char keyPtr[kCCKeySizeAES256+1];
    bzero(keyPtr, sizeof(keyPtr));
    
    [key getCString:keyPtr maxLength:sizeof(keyPtr) encoding:NSUTF8StringEncoding];
    
    NSUInteger dataLength = [self length];
    
    size_t bufferSize = dataLength + kCCKeySizeAES128;
    void *buffer = malloc(bufferSize);
    
    size_t numByteEncrypted = 0;
    CCCryptorStatus cryptStatus = CCCrypt(kCCEncrypt, kCCAlgorithmAES128, kCCOptionPKCS7Padding, keyPtr, kCCKeySizeAES256, NULL, [self bytes], dataLength, buffer, bufferSize, &numByteEncrypted);
    
    if (cryptStatus == kCCSuccess) {
        return [NSData dataWithBytesNoCopy:buffer length:numByteEncrypted];
    }
    
    free(buffer);
    return nil;
}

-(NSData *)decryptAES256WithKey:(NSString *)key
{
    char keyPtr[kCCKeySizeAES256+1];
    bzero(keyPtr, sizeof(keyPtr));
    
    [key getCString:keyPtr maxLength:sizeof(keyPtr) encoding:NSUTF8StringEncoding];
    
    NSUInteger dataLength = [self length];
    
    size_t bufferSize = dataLength + kCCKeySizeAES128;
    void *buffer = malloc(bufferSize);
    
    size_t numByteDecrypted = 0;
    CCCryptorStatus cryptStatus = CCCrypt(kCCDecrypt, kCCAlgorithmAES128, kCCOptionPKCS7Padding, keyPtr, kCCKeySizeAES256, NULL, [self bytes], dataLength, buffer, bufferSize, &numByteDecrypted);
    
    if (cryptStatus == kCCSuccess) {
        return [NSData dataWithBytesNoCopy:buffer length:numByteDecrypted];
    }
    
    free(buffer);
    return nil;
}

@end
