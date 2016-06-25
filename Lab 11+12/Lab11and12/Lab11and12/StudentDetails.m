//
//  StudentDetails.m
//  Lab11-Sqlite
//
//  Created by Aditya Narasimhamurthy on 4/18/15.
//  Copyright (c) 2015 Aditya Narasimhamurthy. All rights reserved.
//
#import "StudentDetails.h"

@interface StudentDetails()
@property (weak, nonatomic) IBOutlet UITextField *studentNameTF;
@property (weak, nonatomic) IBOutlet UITextField *studentIDTF;
@property (weak, nonatomic) IBOutlet UITextField *studentMajorTF;
@property (weak, nonatomic) IBOutlet UITextField *studentEmailTF;
@property (weak, nonatomic) IBOutlet UITextField *studentAddcourseTF;
@property (weak, nonatomic) IBOutlet UITextField *studentDropcourseTF;
@property (weak, nonatomic) IBOutlet UIButton *selectStudent;




@end

@implementation StudentDetails

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.title = @"Student Details";
    
    self.studentIDTF.keyboardType = UIKeyboardTypeNumberPad;
    
    
    
    if(self.isNewStudent == YES){
        self.title = @"Add Student";
        UIBarButtonItem *btnAdd = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemSave target:self action:@selector(addClicked:)];
        self.navigationItem.rightBarButtonItem = btnAdd;
        self.isNewStudent = NO;
        [self.selectStudent setTag:1234];
        [self.selectStudent addTarget:self action:@selector(selectStudentClicked:) forControlEvents:UIControlEventTouchUpInside];
    }
    else{
        self.title = @"Student Details";
        
        self.selectStudent.hidden = YES;
        
        UIBarButtonItem *btnDel = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemTrash target:self action:@selector(delClicked:)];
        UIBarButtonItem *btnDrop = [[UIBarButtonItem alloc] initWithTitle:@"Drop" style:UIBarButtonItemStylePlain target:self action:@selector(dropClicked:)];
        
        
        self.navigationItem.rightBarButtonItems =@[btnDel,btnDrop];
        
        
        [self.studentNameTF setText:[NSString stringWithString:self.studentName]];
        
        NSString * queryString = [[@"select * from student where name='"
                                   stringByAppendingString:self.studentNameTF.text]
                                  stringByAppendingString:@"';"] ;
        //      NSLog(queryString);
        NSArray * queryRes = [self.crsDB executeQuery:queryString];
        
        NSArray * res = queryRes[0];
        
        [self.studentNameTF setText:res[0]];
        [self.studentMajorTF setText:res[1]];
        [self.studentEmailTF setText:res[2]];
        [self.studentIDTF setText:res[3]];
        
        
    }
    
    
    
    
    
    
    
}

- (void) addClicked:(id)sender{
    
    NSNumber *testForNum;
    NSNumberFormatter *formatter = [NSNumberFormatter new];
    testForNum = [formatter numberFromString:self.studentIDTF.text];
    
    
    
    if([self.studentNameTF.text isEqual:@""] || [self.studentMajorTF.text isEqual:@""] || [self.studentEmailTF.text isEqual:@""] || [self.studentIDTF.text isEqual:@""]){
        UIAlertView * alert = [[UIAlertView alloc] initWithTitle:@"Warning!" message:[NSString stringWithFormat:@"Incomplete / Incorrect Input"] delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
        alert.alertViewStyle = UIAlertViewStyleDefault;
        [alert show];
        
    }
    
    else if(!testForNum) {
        UIAlertView * alert = [[UIAlertView alloc] initWithTitle:@"Warning!" message:[NSString stringWithFormat:@"Student ID should accept only numbers"] delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
        alert.alertViewStyle = UIAlertViewStyleDefault;
        [alert show];
    }
    
    else{
        
        
        
        
        //check if the student id exists
        
        NSString * queryString = [[@"select studentid from student where studentid = "
                                   stringByAppendingString:self.studentIDTF.text]
                                  stringByAppendingString:@";"];
        NSArray * checkRes = [self.crsDB executeQuery:queryString];
        if([checkRes count] > 0){
            UIAlertView * alert = [[UIAlertView alloc] initWithTitle:@"Warning!" message:[NSString stringWithFormat:@"Student ID exists, please enter a different ID"] delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
            alert.alertViewStyle = UIAlertViewStyleDefault;
            [alert show];
            
        }else{
            
            
            
            queryString = [[[[[[[[@"insert into student values ('"stringByAppendingString: self.studentNameTF.text]
                                 stringByAppendingString:@"','"]
                                  stringByAppendingString:self.studentMajorTF.text ]
                                     stringByAppendingString:@"','" ]
                                stringByAppendingString:self.studentEmailTF.text ]
                                stringByAppendingString:@"'," ]
                            stringByAppendingString:self.studentIDTF.text]
                           stringByAppendingString:@");"];
            //  NSLog(queryString);
            
            NSArray * addRes = [self.crsDB executeQuery:queryString];
            
            [self.navigationController popViewControllerAnimated:YES];
        }
    }
}



-(void)delClicked:(id)sender{
    
    // delete from course where coursename='CSE 598 DAA';
    
    //get courseid
    
    UIAlertView *deleteAlert = [[UIAlertView alloc] initWithTitle:@"Warning"
                                                          message:[[@"Remove Student '" stringByAppendingString: self.studentName] stringByAppendingString:@"' ?"]
                                                         delegate:self
                                                cancelButtonTitle:@"NO"
                                                otherButtonTitles:@"YES", nil];
    [deleteAlert show];
    
}

-(void)dropClicked:(id)sender{
    //get courseid
    
    UIAlertView *dropAlert = [[UIAlertView alloc] initWithTitle:@"Drop"
                                                        message:[[[[@"Drop Student '" stringByAppendingString: self.studentName] stringByAppendingString:@"' from course '"]
                                                                  stringByAppendingString: self.selectedCourse]
                                                                 stringByAppendingString:@"' ?"]
                                                       delegate:self
                                              cancelButtonTitle:@"NO"
                                              otherButtonTitles:@"YES", nil];
    [dropAlert show];
    
}




- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    //  NSLog(@"Add button Clicked");
    
    
    NSString *title = [alertView title];
    
    
    if([title isEqual:@"Warning"]){
        
        NSString *buttonText = [alertView buttonTitleAtIndex:buttonIndex];
        
        if([buttonText isEqualToString:@"NO"])
        {
            
        }
        else if([buttonText isEqualToString:@"YES"])
        {
            //get the student ID
            NSString* queryString = [[@"select * from student where name = '"
                                      stringByAppendingString: self.studentName]
                                     stringByAppendingString:@"';"];
            //   NSLog(queryString);
            NSMutableArray * queryRes = [self.crsDB executeQuery:queryString];
            
            
            NSArray * studentIDObject = queryRes[0];
            NSString * studentID = studentIDObject[3];
            
            
            //delete course
            queryString = [[@"delete from student where name = '"
                            stringByAppendingString: self.studentName]
                           stringByAppendingString:@"';"];
            //    NSLog(queryString);
            queryRes = [self.crsDB executeQuery:queryString];
            
            
            //remove course for student
            queryString = [[@"delete from studenttakes where studentid = "
                            stringByAppendingString: studentID]
                           stringByAppendingString:@";"];
            //    NSLog(queryString);
            queryRes = [self.crsDB executeQuery:queryString];
            
            
            
            [self.navigationController popViewControllerAnimated:YES];
            
            
        }
        
    }
    if([title isEqual:@"Drop"]){
        
        NSString *buttonText = [alertView buttonTitleAtIndex:buttonIndex];
        
        if([buttonText isEqualToString:@"NO"])
        {
            
        }
        else if([buttonText isEqualToString:@"YES"])
        {
            //get the student ID
            NSString* queryString = [[@"select * from student where name = '"
                                      stringByAppendingString: self.studentName]
                                     stringByAppendingString:@"';"];
            //   NSLog(queryString);
            NSMutableArray * queryRes = [self.crsDB executeQuery:queryString];
            
            NSArray * studentIDObject = queryRes[0];
            NSString * studentID = studentIDObject[3];
            
            //get the course ID
            
            queryString = [[@"select * from course where coursename = '"
                            stringByAppendingString: self.selectedCourse]
                           stringByAppendingString:@"';"];
            //   NSLog(queryString);
            queryRes = [self.crsDB executeQuery:queryString];
            
            
            NSArray * courseIDObject = queryRes[0];
            NSString * courseID = courseIDObject[1];
            
            
            
            //drop course for student
            queryString = [[[[@"delete from studenttakes where studentid = "
                              stringByAppendingString: studentID]
                             stringByAppendingString:@" and courseid = "]
                            stringByAppendingString: courseID]
                           stringByAppendingString:@";"];
            //    NSLog(queryString);
            queryRes = [self.crsDB executeQuery:queryString];
            
            
            
            [self.navigationController popViewControllerAnimated:YES];
            
            
        }
        
    }
}



- (IBAction)selectStudentClicked:(id)sender{
    
    [self.studentNameTF setText:@""];
    [self.studentEmailTF setText:@""];
    [self.studentIDTF setText:@""];
    [self.studentMajorTF setText:@""];
    
    
    ABPeoplePickerNavigationController *picker =
    [[ABPeoplePickerNavigationController alloc] init];
    picker.peoplePickerDelegate = self;
    
    [self presentModalViewController:picker animated:YES];
    
}

- (void)peoplePickerNavigationControllerDidCancel:
(ABPeoplePickerNavigationController *)peoplePicker
{
    [self dismissModalViewControllerAnimated:YES ];
}

- (BOOL)peoplePickerNavigationController:
(ABPeoplePickerNavigationController *)peoplePicker
      shouldContinueAfterSelectingPerson:(ABRecordRef)person
                                property:(ABPropertyID)property
                              identifier:(ABMultiValueIdentifier)identifier
{
    return NO;
}

/*
 
 - (BOOL)peoplePickerNavigationController:
 (ABPeoplePickerNavigationController *)peoplePicker
 shouldContinueAfterSelectingPerson:(ABRecordRef)person {
 
 [self displayPerson:person];
 [self dismissModalViewControllerAnimated:YES];
 
 return NO;
 }
 */
- (void)peoplePickerNavigationController:
(ABPeoplePickerNavigationController *)peoplePicker
                         didSelectPerson:(ABRecordRef)person {
    
    [self displayPerson:person];
    [self dismissModalViewControllerAnimated:YES];
    
}


- (void)displayPerson:(ABRecordRef)person
{
    NSString* name = (__bridge_transfer NSString*)ABRecordCopyValue(person,
                                                                    kABPersonFirstNameProperty);
    [self.studentNameTF setText:name];
    
    ABMultiValueRef emailMultiValue = ABRecordCopyValue(person, kABPersonEmailProperty);
    NSArray *emailAddresses = (__bridge NSArray *)ABMultiValueCopyArrayOfAllValues(emailMultiValue);
    
    if(emailAddresses.count > 0){
        [self.studentEmailTF setText:[emailAddresses objectAtIndex:0]];
        CFRelease(emailMultiValue);
    }
    
    
    
}





@end
