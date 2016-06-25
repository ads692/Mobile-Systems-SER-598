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

#import "DetailsViewController.h"

@interface DetailsViewController ()
@property (weak, nonatomic) IBOutlet UITextField *nameTextView;
@property (weak, nonatomic) IBOutlet UITextField *latTextView;
@property (weak, nonatomic) IBOutlet UITextField *lonTextView;
@property (weak, nonatomic) IBOutlet UITextField *distanceTextView;
@property (weak, nonatomic) IBOutlet UITextField *toTextView;
@property (weak, nonatomic) IBOutlet UITextField *addressTextField;
@property (weak, nonatomic) IBOutlet UITextField *categoryTextField;

@property (strong, atomic) UIPickerView *toPicker;
@end

@implementation DetailsViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    self.latTextView.delegate = self;
    self.latTextView.keyboardType = UIKeyboardTypeNumbersAndPunctuation;
    self.lonTextView.delegate = self;
    self.lonTextView.keyboardType = UIKeyboardTypeNumbersAndPunctuation;
    
    
    self.toPicker= [[UIPickerView alloc] init];
    self.toPicker.delegate=self;
    self.toPicker.dataSource=self;

    self.toTextView.inputView = self.toPicker ;
    
    Waypoint *selectedWP = [self.waypointLibrary getWaypointNamed:self.selectedWaypoint];
    self.nameTextView.text = selectedWP.name;
    self.lonTextView.text= [NSString stringWithFormat:@"%f", selectedWP.lon];
    self.latTextView.text= [NSString stringWithFormat:@"%f", selectedWP.lat];
    self.categoryTextField.text = selectedWP.category;
    self.addressTextField.text = selectedWP.address;
    
    self.navigationItem.title = self.selectedWaypoint;

}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (IBAction)removeClicked:(id)sender {
    [self.waypointLibrary removeWaypointNamed:[self.nameTextView text]];
    [self.navigationController popViewControllerAnimated:YES];
}

- (void) pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component{
    if(pickerView == self.toPicker){
        [self.toPicker resignFirstResponder];
        Waypoint *toWP = [self.waypointLibrary getWaypointNamed:self.waypointLibrary.getNames[row]];
        [self.toTextView setText:toWP.name];
        NSString *fromName =[self.nameTextView text];
        Waypoint *fromWP = [self.waypointLibrary getWaypointNamed:fromName];
        NSLog(@"to picker selected. From = %@",fromName);
        if(fromWP != nil){
            NSLog(@"setting distance and bearing");
            double dist = [fromWP distanceGCTo:toWP.lat lon:toWP.lon scale:Kmeter];
            double bearing = [fromWP bearingGCInitTo:toWP.lat lon:toWP.lon];
            [self.distanceTextView setText:[NSString stringWithFormat:@" %.2f KM Bearing %.2f",dist, bearing]];
        }
    }
}

- (NSInteger) numberOfComponentsInPickerView:(UIPickerView *)pickerView{
    return 1;
}

- (NSString*) pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component{
    NSArray *names =[self.waypointLibrary getNames];
    NSString *returnValue = @"unknown";
    if(row < names.count){
        returnValue = names[row];
    }
    return returnValue;
}

- (NSInteger) pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component{
    return [self.waypointLibrary getNames].count;
}

-(BOOL) textFieldShouldReturn:(UITextField *)textField{
    [textField resignFirstResponder];
    return YES;
}

-(void) touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event{
    [self.latTextView resignFirstResponder];
    [self.lonTextView resignFirstResponder];
    [self.nameTextView resignFirstResponder];
    [self.toTextView resignFirstResponder];
    [self.distanceTextView resignFirstResponder];
    [self.toPicker resignFirstResponder];
}
@end
