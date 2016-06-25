/**
 * Copyright (C) 2015 Aditya Narasimhamurthy
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Aditya Narasimhamurthy mailto:aditya.murthy@asu.edu
 * @version March 28, 2015
 */

#import "AddWaypointController.h"

@interface AddWaypointController ()
@property (weak, nonatomic) IBOutlet UITextField *nameTV;
@property (weak, nonatomic) IBOutlet UITextField *latTV;
@property (weak, nonatomic) IBOutlet UITextField *lonTV;
@property (weak, nonatomic) IBOutlet UITextField *addrTV;
@property (weak, nonatomic) IBOutlet UITextField *catTV;
@end

@implementation AddWaypointController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.latTV.delegate = self;
    self.latTV.keyboardType = UIKeyboardTypeNumbersAndPunctuation;
    self.lonTV.delegate = self;
    self.lonTV.keyboardType = UIKeyboardTypeNumbersAndPunctuation;
    
    
    UIBarButtonItem *addButton = [[UIBarButtonItem alloc] initWithTitle:@"Save" style:(UIBarButtonItemStylePlain) target:self action:@selector(saveClicked)];
    self.navigationItem.rightBarButtonItem = addButton;
    
    UIBarButtonItem *cancelButton = [[UIBarButtonItem alloc] initWithTitle:@"Cancel" style:(UIBarButtonItemStylePlain) target:self action:@selector(cancelClicked)];
    self.navigationItem.leftBarButtonItem = cancelButton;

    self.navigationItem.title = @"Add waypoint";
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void) saveClicked {

        Waypoint *wp = [[Waypoint alloc] initWithLat:[self.latTV.text doubleValue] lon:[self.lonTV.text doubleValue] name:self.nameTV.text address:self.addrTV.text category:self.catTV.text];
        [self.waypointLibrary addWaypoint:wp];
        [self.navigationController popViewControllerAnimated:YES];

}

- (void) cancelClicked {
    [self.navigationController popViewControllerAnimated:YES];
}


-(BOOL) textFieldShouldReturn:(UITextField *)textField{
    [textField resignFirstResponder];
    return YES;
}

-(void) touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event{
    [self.latTV resignFirstResponder];
    [self.lonTV resignFirstResponder];
    [self.nameTV resignFirstResponder];
    [self.addrTV resignFirstResponder];
    [self.catTV resignFirstResponder];
}

@end
