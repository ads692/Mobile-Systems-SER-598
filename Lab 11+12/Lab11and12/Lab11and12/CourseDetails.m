//
//  CourseDetails.m
//  Lab11-Sqlite
//
//  Created by Aditya Narasimhamurthy on 4/18/15.
//  Copyright (c) 2015 Aditya Narasimhamurthy. All rights reserved.
//
#import "CourseDetails.h"

@interface CourseDetails()
@property (weak, nonatomic) IBOutlet UITextField *coursenameTF;
@property (weak, nonatomic) IBOutlet UITextField *courseIDTF;



@end

@implementation CourseDetails
- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.courseIDTF.keyboardType = UIKeyboardTypeNumberPad;
    UIBarButtonItem *saveButton = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemSave target:self action:@selector(saveClicked:)];
    
    self.navigationItem.rightBarButtonItem = saveButton;
}

- (void)saveClicked:(id)sender{
    NSNumber *testForNum;
    NSNumberFormatter *formatter = [NSNumberFormatter new];
    testForNum = [formatter numberFromString:self.courseIDTF.text];
    if([self.coursenameTF.text isEqual:@""] || [self.courseIDTF.text isEqual:@""]){
        UIAlertView * alert = [[UIAlertView alloc] initWithTitle:@"Warning!" message:[NSString stringWithFormat:@"Incomplete Input"] delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
        alert.alertViewStyle = UIAlertViewStyleDefault;
        [alert show];
        
    }
    else if(!testForNum) {
        UIAlertView * alertbox = [[UIAlertView alloc] initWithTitle:@"Warning!" message:[NSString stringWithFormat:@"Course ID should be a number"] delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
        alertbox.alertViewStyle = UIAlertViewStyleDefault;
        [alertbox show];
    }
    
    else{
        
        
        //get the course ID
        
        NSString* queryString = [[@"select courseid from course where courseid = "
                                  stringByAppendingString: self.courseIDTF.text]
                                 stringByAppendingString:@";"];
        //  NSLog(queryString);
        NSArray * queryRes = [self.courseDB executeQuery:queryString];
        
        if([queryRes count] > 0){
            UIAlertView * alert = [[UIAlertView alloc] initWithTitle:@"Warning!" message:[NSString stringWithFormat:@"Course ID exists"] delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
            alert.alertViewStyle = UIAlertViewStyleDefault;
            [alert show];
            
        }else{
            
            
            
            
            
            NSString * queryString = [[[[@"insert into course values ('"
                                         stringByAppendingString: self.coursenameTF.text]
                                        stringByAppendingString:@"',"]
                                       stringByAppendingString:self.courseIDTF.text]
                                      stringByAppendingString:@");"];
            //     NSLog(queryString);
            
            NSArray * addRes = [self.courseDB executeQuery:queryString];
            
            [self.navigationController popViewControllerAnimated:YES];
        }
    }
    
    //[self.courseTableView reloadData];
    
    
    //   [self performSegueWithIdentifier:@"CourseDetails" sender:sender];
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    
    NSString *title = [alertView title];
    
    
    if([title isEqual:@"Warning"]){
        
        NSString *buttonText = [alertView buttonTitleAtIndex:buttonIndex];
        
        if([buttonText isEqualToString:@"NO"])
        {
            
        }
        else if([buttonText isEqualToString:@"YES"])
        {
            
            
        }
    }
    
}



@end
