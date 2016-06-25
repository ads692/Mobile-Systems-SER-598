//
//  StudentDetails.h
//  Lab11-Sqlite
//
//  Created by Aditya Narasimhamurthy on 4/18/15.
//  Copyright (c) 2015 Aditya Narasimhamurthy. All rights reserved.
//
#import <Foundation/Foundation.h>
#import <WebKit/WebKit.h>
#import "CourseDBManager.h"
#import <AddressBookUI/AddressBookUI.h>

@interface StudentDetails : UIViewController <ABPeoplePickerNavigationControllerDelegate>
@property (nonatomic,assign) BOOL * isNewStudent;
@property (strong, nonatomic) CourseDBManager * crsDB;
@property (strong, nonatomic) NSString * studentName;
@property (strong, nonatomic) NSString * selectedCourse;
- (IBAction)showPicker:(id)sender;
@end
