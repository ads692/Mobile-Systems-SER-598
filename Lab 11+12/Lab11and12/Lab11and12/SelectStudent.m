//
//  SelectStudent.m
//  Lab11-Sqlite
//
//  Created by Aditya Narasimhamurthy on 4/18/15.
//  Copyright (c) 2015 Aditya Narasimhamurthy. All rights reserved.
//
#import "SelectStudent.h"


@interface SelectStudent()

@property (weak, nonatomic) IBOutlet UILabel *courseNameTF;
@property (weak, nonatomic) IBOutlet UITextField *studentNameTF;
@property (strong, nonatomic) IBOutlet UIPickerView *studentPicker;


@end

@implementation SelectStudent

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.studentNameTF becomeFirstResponder];
    
    [self.courseNameTF setText:[[@"'" stringByAppendingString:self.selectedCourse ] stringByAppendingString:@"'"]];
    self.studentPicker = [[UIPickerView alloc] init];
    self.studentPicker.delegate = self;
    self.studentPicker.dataSource = self;
    
    
    
    UIBarButtonItem *btnCancel = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel target:self action:@selector(cancelClicked:)];
    
    UIBarButtonItem *btnSave = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemSave target:self action:@selector(saveClicked:)];
    
    self.navigationItem.leftBarButtonItem = btnCancel;
    
    self.navigationItem.rightBarButtonItem = btnSave;
    
    
    NSString*  queryString = [[[[@"select distinct name from student,studenttakes,course where course.coursename = '"
                                 stringByAppendingString: self.selectedCourse]
                                stringByAppendingString:@"' and course.courseid != studenttakes.courseid and student.studentid = studenttakes.studentid and student.name not in (select name from student,studenttakes,course where course.coursename = '"]
                               stringByAppendingString:self.selectedCourse]
                              stringByAppendingString:@"' and course.courseid = studenttakes.courseid and student.studentid = studenttakes.studentid);"];
    
    NSString* queryString1 = @"select name from student where student.name not in (select student.name from student,studenttakes where student.studentid = studenttakes.studentid)";
    
    
    // NSLog(queryString);
    
    NSMutableArray * queryRes =  [self.crsDB executeQuery:queryString];
    NSMutableArray * queryRes1 =  [self.crsDB executeQuery:queryString1];
    
    self.studentList = [[NSMutableArray alloc] init];
    
    for(NSArray* studentIDObject in queryRes){
        NSString* student = studentIDObject[0];
        [self.studentList addObject:student];
    }
    
    for(NSArray* studentIDObject in queryRes1){
        NSString* student = studentIDObject[0];
        [self.studentList addObject:student];
    }
    
    if([self.studentList count] < 1)
    {
        [self.studentNameTF setText:@"All students enrolled !"];
        self.navigationItem.rightBarButtonItem = nil;
    }else{
        self.studentNameTF.inputView = self.studentPicker;
    }
    
    
    
    
}

-(void)saveClicked:(id)sender{
    
    if([self.studentNameTF isEqual:@""]){
        UIAlertView * alert = [[UIAlertView alloc] initWithTitle:@"Warning!" message:[NSString stringWithFormat:@"Incomplete Input"] delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
        alert.alertViewStyle = UIAlertViewStyleDefault;
        [alert show];
    }else{
        
        //get the Course ID
        NSString* queryString = [[@"select * from course where coursename = '"
                                  stringByAppendingString: self.selectedCourse]
                                 stringByAppendingString:@"';"];
        // NSLog(queryString);
        NSMutableArray * queryRes = [self.crsDB executeQuery:queryString];
        NSArray * courseIDObject = queryRes[0];
        NSString * courseID = courseIDObject[1];
        
        
        NSString * studentName = self.studentNameTF.text;
        //get the student ID
        queryString = [[@"select * from student where name = '"
                        stringByAppendingString: studentName]
                       stringByAppendingString:@"';"];
        
        queryRes = [self.crsDB executeQuery:queryString];
        NSArray * studentIDObject = queryRes[0];
        NSString * studentID = studentIDObject[3];
        
        
        
        //insert into student takes
        queryString = [[[[@"insert into studenttakes values("
                          stringByAppendingString: studentID]
                         stringByAppendingString:@"," ]
                        stringByAppendingString:courseID]
                       stringByAppendingString:@");"];
        queryRes = [self.crsDB executeQuery:queryString];
        
        [self.navigationController popViewControllerAnimated:YES];
    }
    
}


-(void)cancelClicked:(id)sender{
    [self.navigationController popViewControllerAnimated:YES];
    
}


- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
    
    NSArray * keys = self.studentList;
    if(row < keys.count){
        
        
        if(pickerView == self.studentPicker){
            [self.studentPicker resignFirstResponder];
            
            [self.studentNameTF setText:keys[row]];
            
        }
    }
    
}

- (NSInteger) numberOfComponentsInPickerView:(UIPickerView *)pickerView{
    return 1;
}

- (NSString*) pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component{
    
    NSArray * keys = self.studentList;
    
    
    NSString * returnString = @"Unknown Key";
    if(row < keys.count){
        returnString = keys[row];
    }
    //NSLog(@"%@",returnString);
    return returnString;
}

- (NSInteger) pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component{
    
    return [self.studentList count];
}



@end
