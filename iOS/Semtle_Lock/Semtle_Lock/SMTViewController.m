//
//  SMTViewController.m
//  Semtle_Lock
//
//  Created by MB KWON on 2013. 12. 5..
//  Copyright (c) 2013년 Semtle_men. All rights reserved.
//

#import "SMTViewController.h"
#import "SMTLockController.h"

@interface SMTViewController ()

@end

@implementation SMTViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    [_passwordText becomeFirstResponder];
    _lockController = [[SMTLockController alloc] init];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


-(IBAction)setPassword:(id)sender
{
    SMT_RESULT_CODE result = [_lockController setPassword:[_passwordText text]];
    
    if (result == SMT_LOCK_OK) {
        NSLog(@"password is set");
        [_lockImage setHidden:NO];
        [_lockImage setSelected:NO];
    } else {
        NSLog(@"ERROR : %lx", (long)result);
    }
}

-(IBAction)login:(id)sender
{
    SMT_RESULT_CODE result = [_lockController matchPassword:[_passwordText text]];
    
    if (result == SMT_LOCK_OK) {
        NSLog(@"password is matched");
        [_lockImage setSelected:YES];
    } else {
        NSLog(@"ERROR : %lx", (long)result);
    }
}

@end
