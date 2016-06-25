//
//  StudentTableViewController.m
//  Lab11-Sqlite
//
//  Created by Aditya Narasimhamurthy on 4/18/15.
//  Copyright (c) 2015 Aditya Narasimhamurthy. All rights reserved.
//

#import "StudentTVController.h"
#import "ViewController.h"
#import "StudentDetails.h"
#import "SelectStudent.h"

@interface StudentTVController()
@property (strong, nonatomic) IBOutlet UITableView *studentTable;
@property (strong, nonatomic) NSMutableArray * studentList;

@property (strong, nonatomic) NSString * query;

@property (strong, nonatomic) NSArray * studentListquery;
@property (strong, nonatomic) id  tempSender;


@end

@implementation StudentTVController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.studentTable.dataSource = self;
    self.query = [NSString stringWithFormat:@"select name from student,studenttakes,course where course.coursename = '%@' and course.courseid = studenttakes.courseid and student.studentid = studenttakes.studentid;",self.selectedCourse];
    
    self.navigationItem.title = self.selectedCourse;
    
    UIBarButtonItem *deleteButton = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemTrash target:self action:@selector(delClicked:)];
    
    UIBarButtonItem *add_Button = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemAdd target:self action:@selector(addClicked:)];
    
    self.navigationItem.rightBarButtonItems = @[deleteButton,add_Button];
    
    
    
}

-(void)delClicked:(id)sender{
    
    
    
    
    UIAlertView *deleteAlert = [[UIAlertView alloc] initWithTitle:@"Warning"
                                                          message:[[@"Remove Course '" stringByAppendingString: self.selectedCourse] stringByAppendingString:@"' ?"]
                                                         delegate:self
                                                cancelButtonTitle:@"NO"
                                                otherButtonTitles:@"YES", nil];
    [deleteAlert show];
    
    
}

-(void)addClicked:(id)sender{
    
    
    self.tempSender = sender;
    
    UIAlertView *addAlert = [[UIAlertView alloc] initWithTitle:@"Add"
                                                       message:@"Add a student to this course ?"
                                                      delegate:self
                                             cancelButtonTitle:@"NO"
                                             otherButtonTitles:@"YES", nil];
    [addAlert show];
    
    
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
            //get the Course ID
            NSString* queryString = [[@"select * from course where coursename = '"
                                      stringByAppendingString: self.selectedCourse]
                                     stringByAppendingString:@"';"];
            // NSLog(queryString);
            NSMutableArray * queryRes = [self.crsDB executeQuery:queryString];
            
            
            NSArray * courseIDObject = queryRes[0];
            NSString * courseID = courseIDObject[1];
            
            
            //delete course
            queryString = [[@"delete from course where coursename = '"
                            stringByAppendingString: self.selectedCourse]
                           stringByAppendingString:@"';"];
            //  NSLog(queryString);
            queryRes = [self.crsDB executeQuery:queryString];
            
            
            //remove course for student
            queryString = [[@"delete from studenttakes where courseid = "
                            stringByAppendingString: courseID]
                           stringByAppendingString:@";"];
            // NSLog(queryString);
            queryRes = [self.crsDB executeQuery:queryString];
            
            
            
            [self.navigationController popViewControllerAnimated:YES];
            
            
        }
        
    }else if([title isEqual:@"Add"]){
        
        NSString *buttonText = [alertView buttonTitleAtIndex:buttonIndex];
        
        if([buttonText isEqualToString:@"NO"])
        {
            
        }
        else if([buttonText isEqualToString:@"YES"])
        {
            
            [self performSegueWithIdentifier:@"SelectStudent" sender:self.tempSender];
            // add code to select student from picker and link it to the course
            
            
            //   [self.navigationController popViewControllerAnimated:YES];
            
            
        }
        
    }
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
#warning Potentially incomplete method implementation.
    // Return the number of sections.
    return 1;
}

-(void) viewWillAppear:(BOOL)animated{
    [self.studentTable reloadData];
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    NSArray * queryRes = [self.crsDB executeQuery:self.query];
    return queryRes.count;
    
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"Cell" forIndexPath:indexPath];
    
    
    NSArray * queryResult = [self.crsDB executeQuery:self.query];
    
    NSString * whichStudent = @"unknown";
    if(queryResult.count> indexPath.row){
        whichStudent = queryResult[indexPath.row][0];
    }
    
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier: @"Cell"];
    }
    cell.accessoryType =  UITableViewCellAccessoryDisclosureIndicator;
    cell.textLabel.text = whichStudent;
    
    return cell;
    
    
    
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    
    if([segue.identifier isEqualToString:@"StudentDetails"]){
        NSIndexPath * indexPath = [self.studentTable indexPathForSelectedRow];
        NSMutableArray  *queryRes = [self.crsDB executeQuery:self.query];
        NSMutableArray * keys = queryRes;
        NSMutableArray *ret  =@"Unknown";
        if(indexPath.row < keys.count){
            ret = keys[[indexPath row]];
        }
        
        NSString *returnValue = ret[0];
        
        StudentDetails * destViewController = segue.destinationViewController;
        destViewController.crsDB = self.crsDB;
        destViewController.studentName = returnValue;
        destViewController.selectedCourse = self.selectedCourse;
    }else if([segue.identifier isEqualToString:@"SelectStudent"]){
        SelectStudent * destViewController = segue.destinationViewController;
        destViewController.selectedCourse = self.selectedCourse;
        destViewController.crsDB = self.crsDB;
        
        
    }
    
}

@end
