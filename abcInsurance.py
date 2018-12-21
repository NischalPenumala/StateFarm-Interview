# -*- coding: utf-8 -*-
"""
Created on Thu Dec 20 04:11:19 2018

@author: nischal
Adding customers, policies and removing the customers, policies for the abc insurance company
>Log in system allows admin to create customers, policies and removing policies.
>Using phython dictionaries and lists to store customers and policy data.

"""

#login and password
admins ={'admin':'pass@123','user1':'user@123'}

#Creating customer dictionary: key as customer name and list of policies as values
customerDictionary = {'Jeff':['Auto','Homeowners','Condo Owners','Renters','Pet Insurance','Life'],
                      'Alex':['Auto','Home','Life','Hospital Income','Medicare Supplement'],
                      'Sony':['Auto','Homeowners','Rentera','Life'],
                      'Nischal':['Auto','Renters','Life','Hospital Income','Medicare Supplement'],
                      'Vahini':['Homeowners','Condo Owners','Renters','Pet Insurance','Life'],
                      'Rishi':['Auto','Homeowners','Condo Owners','Renters','Pet Insurances','Life','Hospital Income','MEdicare Supplements'],
                      'Micheal':['Condo Owners','Renters','Pet Insurance','Life','Hospital Income'],
                      'Rachea':['Auto','Homeowner','Renters','Life']
                      }
     
#Adding customer
def addCustomer():
    customerName = input('Enter new customer name: ')
    policyName = input('Enter new policy: ')
    customerDictionary.update({customerName:[policyName]})
    print(customerDictionary)
                 
#Adding policy to the customer
def addPolicytoCustomer():
    customerName = input('Customer Name: ')
    customerInsurancePolicy = input('Enter the policy name: ')
    
    if customerName in customerDictionary:
        print('Adding policy ...')
        customerDictionary[customerName].append(customerInsurancePolicy)
    else:
        print('Customer does not exits. ')
    
    print(customerDictionary)    

#Removing the customer
def removeCustomer():
    customerToRemove = input('Enter the customer name to remove: ')
    if customerToRemove in customerDictionary:
        print('Removing customer...')
        del customerDictionary[customerToRemove]

    print(customerDictionary)

#Removing the policy on the customer
def removePolicy():
    customerName = input('Customer Name: ')
    customerInsurancePolicy = input('Enter policy name: ')
    if customerName in customerDictionary:
        print('Removing Policy... ')
        customerDictionary[customerName].remove(customerInsurancePolicy)
    else:
        print('Customer does not exits.. ')
        
    print(customerDictionary)

# Welcome page
def main():
    print("""
    Welcome to abc insurance
    
    [1] - Add customer
    [2] - Add Policy for the Customer
    [3] - Remove customer
    [4] - Remove policy for the customer
    [5] - Exit
    """)
    
    action = input('Chose the options to proceed with abc insurance: ')   
    
    if action == '1':
        addCustomer()
    elif action =='2':
        addPolicytoCustomer()
    elif action == '3':
        removeCustomer()
    elif action == '4':
        removePolicy()
    elif action == '5':
        exit()
    else:
        print('No valid option was selected, try again ')

login = input('Username: ')
passw = input('Password: ')
     
#Login verification
if login in admins:
    if admins[login] == passw:
        print('Welcome... ', login) 
        while True:
            main()
    else:
        print('Incorrect password, try again')
else:
    print('Invalid username, create user and login again')
    
    
    
    