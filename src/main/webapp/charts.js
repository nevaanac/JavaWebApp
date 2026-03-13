const genderData = JSON.parse(document.getElementById('genderJson').textContent);
const raceData   = JSON.parse(document.getElementById('raceJson').textContent);

new Chart(document.getElementById('genderChart'), {
  type: 'pie',
  data: {
    labels: genderData.map(d => d.label),
    datasets: [{
      data: genderData.map(d => d.value),
      backgroundColor: ['#2563eb', '#f59e0b', '#10b981', '#6366f1', '#ef4444']
    }]
  },
  options: { plugins: { legend: { position: 'bottom' } } }
});

new Chart(document.getElementById('raceChart'), {
  type: 'bar',
  data: {
    labels: raceData.map(d => d.label),
    datasets: [{
      label: 'Patients',
      data: raceData.map(d => d.value),
      backgroundColor: '#2563eb'
    }]
  },
  options: {
    plugins: { legend: { display: false } },
    scales: { y: { beginAtZero: true, ticks: { stepSize: 1 } } }
  }
});
