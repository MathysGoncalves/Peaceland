
# Peaceland 

## Description

### Client

Peaceland is a blessed country, led by an affable and clear-sighted ruler. He takes great pride in its effort to bring peace, happiness, and harmony to all its citizens.
To do so, they heavily rely on their peacemakers. A governmental agency
dedicated to make peace around the country. To reach their ambition, they bring assistance to any agitated person and help them to recover peace. More generally they help citizen to stay in line with their country harmonious goal. To help its peacemakers squads, Peaceland engineers have created a working autonomous drone called peacewatcher.
They need you to create the program that will receive and manage peacewatchers’s data.

This program must :
- store every peacewatcher data
- trigger alerts
- enable peacemaker officers to perform analysis on peacewatcher data


### Drone description

Each peacewatcher sends a report every minute.
One report contains
- peacewatcher id
- peacewatcher current location (latitude, longitude)
- name of surrounding citizen (identify with facial recognition) with their computed «peacescore»
- words heard by the peacewatcher in its surrounding


### Alert

When a citizen peacescore is bad, your program must trigger an alert with the location of the peacewatcher and the name of the agitated citizen.
Peacemakers will take it from there and help the person to find peace.
They may send him to a peacecamp. In such camp citizen learn to reach
happiness following the ideas of the beneveland leader of Peaceland. Or they will put him in a sustainable and never ending peace state.
This alert must be triggered as quickly as possible because an agitated citizen may spread its lack of peace to other citizens. Thus, the peacemaker reaction must be as fast as possible.


### Statistics

Peacemakers are convinced that we need to keep every peacewatcher report in order to make statistics and improve their Peaceland harmony. But they still don’tknow what kind of question/statistic they will want to address. Peaceland engineer estimate that when the first wave of peacewatcher will be operational the sum of all their daily report will weight 200Gb.
They also estimate that less than 1% of peacewatcher report contains alert.

### Failed attempt

To create a POC of the program, Peaceland hired a team of data-scientists and
Despite all their efforts, this team have not been able to set up a scalable
program that can handle the load.


## Preliminary questions

1) What technical/business constraints should the data storage component of the program architecture meet to fulfill the requirement described by the customer in paragraph «Statistics» ?
So what kind of component(s) (listed in the lecture) will the architecture need?

> From the user's point of view, the data storage will have to handle large and fast data volumes. Also, we have no idea of ​​the use of the stored data nor the duration of the storage. Availability is the most important property in this section we offer an AP solution. Peaceland will therefore need a distributed DataLake updated every day (batch).


2) What business constraint should the architecture meet to fulfill the requirement describe in the paragraph «Alert»? Which component to choose?

> It's necessary to have the best reactivity when it comes to alerts in order to apprehend unhappy citizens before it's to late. Also, a small part of report contains alert. Here we recommend stream processing for this kind of use case.

3) What mistake(s) from Peaceland can explain the failed attempt?

> Data engineers are more likely to succeed in that task because they are specialised on the architecture while the data scientists only come afterwards.

4) Peaceland has likely forgotten some technical information in the report sent by the drone. In the future, this information could help Peaceland make its peacewatchers much more efficient. Which information?

> Potentially useful information sent by the peacewatchers are
>    - A timestamp of the report - This is useful to determine how fast the peacemakers reacted to a specific case and/or if there is an increase in lack of peace at a specific time in the day
>    - The gender of the unhappy citizen - Again to know if a specific population needs to be made more peaceful
>    - The workplace/profession of the target - Useful to know if a certain worplace/profession tends to make people particularly unhappy
>    - The number of time a citizen has been sent in a happiness camp - Informs the peacekeepers if there is a need to take a more permanent solution for the citizen's happiness

## Project

Peaceland understands this is beyond their team limits, it can not put in place a programm to
deal with the drone’s data. Peaceland asks you for advice to design an architecture allowing
them to create a product they could sell to different police forces.
It's up to you to report and recommend the right architecture.
Based on the preliminary questions, your solution is very likely to include :
- at least one distributed storage
- at least one distributed stream
- at least two stream consumer
