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
 * @version March 23, 2015
 */

#import "ViewController.h"
#import "Waypoint.h"
#import "WaypointLibrary.h"

@interface ViewController ()
@property (weak, nonatomic) IBOutlet UITextField *nameTextView;
@property (weak, nonatomic) IBOutlet UITextField *latTextView;
@property (weak, nonatomic) IBOutlet UITextField *lonTextView;
@property (weak, nonatomic) IBOutlet UITextField *distanceTextView;
@property (weak, nonatomic) IBOutlet UITextField *toTextView;
@property (weak, nonatomic) IBOutlet UITextField *addressTextField;
@property (weak, nonatomic) IBOutlet UITextField *categoryTextField;

@property (strong, atomic) WaypointLibrary *waypointLibrary;

@property (strong, atomic) UIPickerView *namePicker;
@property (strong, atomic) UIPickerView *toPicker;
@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.waypointLibrary = [[WaypointLibrary alloc] init];
    
    Waypoint * ny = [[Waypoint alloc] initWithLat:40.7127 lon:-74.0059 name:@"New-York" address:@"221 B Street"  category:@"Hiking"];
    Waypoint * asup = [[Waypoint alloc] initWithLat:33.3056 lon:-111.6788 name:@"ASUP" address:@"221 Orange St"  category:@"Travel"];
    Waypoint * asub = [[Waypoint alloc] initWithLat:33.4235 lon:-111.9389 name:@"ASUB" address:@"221 Lemon St"  category:@"School"];
    Waypoint * paris = [[Waypoint alloc] initWithLat:48.8567 lon:2.3508 name:@"Paris" address:@"221 Apple St"  category:@"Travel"];
    Waypoint * moscow = [[Waypoint alloc] initWithLat:55.75 lon:37.6167 name:@"Moscow" address:@"221 McClintock"  category:@"Travel"];
    
    [self.waypointLibrary addWaypoint:ny];
    [self.waypointLibrary addWaypoint:asup];
    [self.waypointLibrary addWaypoint:asub];
    [self.waypointLibrary addWaypoint:paris];
    [self.waypointLibrary addWaypoint:moscow];

    self.latTextView.delegate = self;
    self.latTextView.keyboardType = UIKeyboardTypeNumbersAndPunctuation;
    self.lonTextView.delegate = self;
    self.lonTextView.keyboardType = UIKeyboardTypeNumbersAndPunctuation;
    
    self.namePicker= [[UIPickerView alloc] init];
    self.namePicker.delegate=self;
    self.namePicker.dataSource=self;
    
    self.toPicker= [[UIPickerView alloc] init];
    self.toPicker.delegate=self;
    self.toPicker.dataSource=self;

    self.nameTextView.inputView = self.namePicker;
    self.toTextView.inputView = self.toPicker ;

}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)addClicked:(id)sender {
    NSLog(@"Add clicked");
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Input required" message:@"Please enter waypoint name" delegate:self cancelButtonTitle:@"Cancel" otherButtonTitles:@"OK", nil];
    alertView.alertViewStyle = UIAlertViewStylePlainTextInput;
    [alertView show];
}

-(void)alertView:(UIAlertView *) alertView clickedButtonAtIndex:(NSInteger)buttonIndex{
    NSLog(@"Waypoint name entered %@", [[alertView textFieldAtIndex:0] text]);
    if(buttonIndex == 1){
        if([[alertView textFieldAtIndex:0] text]){
            Waypoint * wp = [[Waypoint alloc] initWithLat:40.7127 lon:-74.0059 name:[[alertView textFieldAtIndex:0] text] address:@"221 B Street"  category:@"Hiking"];
            [self.waypointLibrary addWaypoint:wp];
            [self.namePicker reloadAllComponents];
            [self.toPicker reloadAllComponents];
        }
        
    }
}

- (IBAction)removeClicked:(id)sender {
    NSLog(@"remove clicked");
    BOOL removed = [self.waypointLibrary removeWaypointNamed:[self.nameTextView text]];
    NSString *msg = [NSString stringWithFormat:@"Success :%@",removed?@"true":@"false" ];
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Remove Waypoint" message:msg delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];
    [alertView show];
    [self.namePicker reloadAllComponents];
    [self.namePicker selectRow:0 inComponent:0 animated:YES];
    [self pickerView:self.namePicker didSelectRow:0 inComponent:0];
    [self.toPicker reloadAllComponents];
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
        
    }else{
        [self.namePicker resignFirstResponder];
        Waypoint *wp = [self.waypointLibrary getWaypointNamed:self.waypointLibrary.getNames[row]];
        [self.nameTextView setText:wp.name];
        [self.latTextView setText:[NSString stringWithFormat:@"%.2f", wp.lat]];
        [self.lonTextView setText:[NSString stringWithFormat:@"%.2f", wp.lon]];
        [self.addressTextField setText:wp.address];
        [self.categoryTextField setText:wp.category];
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
    [self.namePicker resignFirstResponder];
    [self.toPicker resignFirstResponder];
}
@end
