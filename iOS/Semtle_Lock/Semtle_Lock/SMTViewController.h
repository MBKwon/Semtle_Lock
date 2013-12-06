//
//  SMTViewController.h
//  Semtle_Lock
//
//  Created by MB KWON on 2013. 12. 5..
//  Copyright (c) 2013년 Semtle_men. All rights reserved.
//

#import <UIKit/UIKit.h>

@class SMTLockController;

@interface SMTViewController : UIViewController

@property (nonatomic, strong) SMTLockController *lockController;
@property (nonatomic, strong) IBOutlet UITextField *passwordText;
@property (nonatomic, strong) IBOutlet UIButton *lockImage;

-(IBAction)setPassword:(id)sender;
-(IBAction)login:(id)sender;

@end
